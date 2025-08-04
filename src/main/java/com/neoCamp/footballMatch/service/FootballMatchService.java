package com.neoCamp.footballMatch.service;

import com.neoCamp.footballMatch.dto.FootballMatchDTO;
import com.neoCamp.footballMatch.dto.FootballMatchDetailsDTO;
import com.neoCamp.footballMatch.dto.rabbitmq.PartidaInformacaoMessage;
import com.neoCamp.footballMatch.entity.ClubEntity;
import com.neoCamp.footballMatch.entity.StadiumEntity;
import com.neoCamp.footballMatch.entity.FootballMatch;
import com.neoCamp.footballMatch.mapper.FootballMatchMapper;
import com.neoCamp.footballMatch.repository.FootballMatchRepository;
import com.neoCamp.footballMatch.repository.StadiumRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class FootballMatchService {

    private static final Logger LOG = LoggerFactory.getLogger(FootballMatchService.class);

    private final FootballMatchRepository footballMatchRepository;
    private final ClubService clubService;
    private final StadiumService stadiumService;
    private final StadiumRepository stadiumRepository;
    private final FootballMatchMessageService footballMatchMessageService;

    @Autowired
    public FootballMatchService(FootballMatchRepository footballMatchRepository,
                                ClubService clubService,
                                StadiumService stadiumService,
                                StadiumRepository stadiumRepository,
                                FootballMatchMessageService footballMatchMessageService) {
        this.footballMatchRepository = footballMatchRepository;
        this.clubService = clubService;
        this.stadiumService = stadiumService;
        this.stadiumRepository = stadiumRepository;
        this.footballMatchMessageService = footballMatchMessageService;
    }

    // ==================== MÉTODOS CRUD (SEM CONVERSÕES) ====================

    public FootballMatchDTO createPartida(FootballMatchDTO dto) {
        try {
            // Chamada direta - seus services esperam Long
            ClubEntity mandante = clubService.findEntityById(dto.getHomeClubId());
            ClubEntity visitante = clubService.findEntityById(dto.getClubVisitorId());
            StadiumEntity estadio = stadiumService.findEntityById(dto.getStadiumId());

            FootballMatch entity = FootballMatchMapper.toEntity(dto, mandante, visitante, estadio);
            FootballMatch saved = footballMatchRepository.save(entity);

            // Enviar mensagem RabbitMQ (sem falhar se der erro)
            enviarMensagemSegura(saved, dto);

            return FootballMatchMapper.toDto(saved);
        } catch (Exception e) {
            LOG.error("Erro ao criar partida: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao criar partida", e);
        }
    }

    public FootballMatchDTO updatePartida(Long id, FootballMatchDTO dto) {
        try {
            FootballMatch entity = footballMatchRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Partida não encontrada!"));

            // Chamada direta - seus services esperam Long
            ClubEntity mandante = clubService.findEntityById(dto.getHomeClubId());
            ClubEntity visitante = clubService.findEntityById(dto.getClubVisitorId());
            StadiumEntity estadio = stadiumService.findEntityById(dto.getStadiumId());

            entity.setHomeClub(mandante);
            entity.setClubVisitor(visitante);
            entity.setStadium(estadio);
            entity.setDateTimeDeparture(dto.getDateTimeDeparture());
            entity.setHomeTeamGoals(dto.getHomeTeamGoals());
            entity.setGoalsVisitor(dto.getGoalsVisitor());

            FootballMatch saved = footballMatchRepository.save(entity);

            // Enviar mensagem RabbitMQ (sem falhar se der erro)
            enviarMensagemSegura(saved, dto);

            return FootballMatchMapper.toDto(saved);
        } catch (Exception e) {
            LOG.error("Erro ao atualizar partida: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao atualizar partida", e);
        }
    }

    public void removerPartida(Long id) {
        footballMatchRepository.deleteById(id);
    }

    public FootballMatchDTO findById(Long id) {
        return footballMatchRepository.findById(id)
                .map(FootballMatchMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Partida não encontrada!"));
    }


    public FootballMatch findEntityById(Long id) {
        return footballMatchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partida não encontrada!"));
    }

    public FootballMatchDetailsDTO findMatchDetailsById(UUID id) {
        FootballMatch match = footballMatchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partida não encontrada!"));

        return FootballMatchMapper.toDetailsDto(match);
    }

    public Page<FootballMatchDTO> listar(Pageable pageable) {
        return footballMatchRepository.findAll(pageable).map(FootballMatchMapper::toDto);
    }


    /**
     * Processa mensagem RabbitMQ recebida
     */
    public void processMatchInfoMessage(PartidaInformacaoMessage messageDto) {
        try {
            if (messageDto == null) {
                throw new IllegalArgumentException("Mensagem não pode ser nula");
            }
            if (messageDto.getIdPartida() == null) {
                throw new IllegalArgumentException("ID da partida é obrigatório");
            }
            if (messageDto.getDataHoraInicio() == null) {
                throw new IllegalArgumentException("Data/hora é obrigatória");
            }

            // Criar partida simples a partir da mensagem
            FootballMatch match = new FootballMatch();
            match.setDateTimeDeparture(messageDto.getDataHoraInicio());

            FootballMatch saved = footballMatchRepository.save(match);

            LOG.info("Partida criada da mensagem: id={}, uuid={}",
                    saved.getId(), messageDto.getIdPartida());
        } catch (Exception e) {
            LOG.error("Erro ao processar mensagem: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao processar mensagem da partida", e);
        }
    }

    /**
     * Envia mensagem RabbitMQ sem quebrar operação principal
     */
    private void enviarMensagemSegura(FootballMatch entity, FootballMatchDTO dto) {
        try {
            PartidaInformacaoMessage message = new PartidaInformacaoMessage();

            // Gerar UUID simples a partir do ID
            UUID matchUuid = gerarUuidDoId(entity.getId());
            message.setIdPartida(matchUuid);
            message.setDataHoraInicio(dto.getDateTimeDeparture());

            footballMatchMessageService.processarPartidaInformacao(message);

            LOG.debug("Mensagem enviada: matchId={}, uuid={}", entity.getId(), matchUuid);
        } catch (Exception e) {
            LOG.warn("Falha ao enviar mensagem (não crítico): {}", e.getMessage());
            // Não quebra a operação principal
        }
    }

    // ==================== UTILITÁRIOS SIMPLES ====================

    /**
     * Gera UUID simples a partir de Long
     */
    private UUID gerarUuidDoId(Long id) {
        if (id == null) {
            return UUID.randomUUID();
        }
        return UUID.nameUUIDFromBytes(("match-" + id).getBytes());
    }

    /**
     * Converte UUID para Long aproximado
     */
    private Long converterUuidParaLong(UUID uuid) {
        if (uuid == null) {
            return null;
        }
        return Math.abs((long) uuid.hashCode());
    }

    // ==================== MÉTODOS PÚBLICOS ADICIONAIS ====================

    /**
     * Busca partida por UUID (aproximado)
     */
    public Optional<FootballMatch> buscarPorUuid(UUID uuid) {
        if (uuid == null) {
            return Optional.empty();
        }

        try {
            Long id = converterUuidParaLong(uuid);
            return footballMatchRepository.findById(id);
        } catch (Exception e) {
            LOG.warn("Erro ao buscar por UUID: {}", e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Gera UUID para uma partida existente
     */
    public UUID obterUuidDaPartida(Long matchId) {
        return gerarUuidDoId(matchId);
    }

    /**
     * Validação completa antes de criar partida
     */
    public FootballMatchDTO createPartidaComValidacao(FootballMatchDTO dto) {
        // Validações básicas
        if (dto.getHomeClubId() == null || dto.getHomeClubId() <= 0) {
            throw new IllegalArgumentException("ID do clube mandante inválido");
        }
        if (dto.getClubVisitorId() == null || dto.getClubVisitorId() <= 0) {
            throw new IllegalArgumentException("ID do clube visitante inválido");
        }
        if (dto.getStadiumId() == null || dto.getStadiumId() <= 0) {
            throw new IllegalArgumentException("ID do estádio inválido");
        }
        if (dto.getDateTimeDeparture() == null) {
            throw new IllegalArgumentException("Data/hora é obrigatória");
        }
        if (dto.getHomeClubId().equals(dto.getClubVisitorId())) {
            throw new IllegalArgumentException("Clube mandante e visitante devem ser diferentes");
        }

        return createPartida(dto);
    }

    /**
     * Método para debug
     */
    public void debugConversoes(Long id) {
        UUID uuid = gerarUuidDoId(id);
        Long voltaParaLong = converterUuidParaLong(uuid);
        LOG.info("ID {}: UUID={}, volta para Long={}", id, uuid, voltaParaLong);
    }
}










//package com.neoCamp.footballMatch.service;
//
//import com.neoCamp.footballMatch.dto.FootballMatchDTO;
//import com.neoCamp.footballMatch.dto.rabbitmq.PartidaInformacaoMessage;
//import com.neoCamp.footballMatch.entity.ClubEntity;
//import com.neoCamp.footballMatch.entity.StadiumEntity;
//import com.neoCamp.footballMatch.entity.FootballMatch;
//import com.neoCamp.footballMatch.mapper.FootballMatchMapper;
//import com.neoCamp.footballMatch.repository.FootballMatchRepository;
//import com.neoCamp.footballMatch.repository.StadiumRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//import java.util.UUID;
//
//@Service
//public class FootballMatchService {
//
//    private static final Logger LOG = LoggerFactory.getLogger(FootballMatchService.class);
//
//    private final FootballMatchRepository footballMatchRepository;
//    private final ClubService clubService;
//    private final StadiumService stadiumService;
//    private final StadiumRepository stadiumRepository;
//    private final FootballMatchMessageService footballMatchMessageService;
//
//    @Autowired
//    public FootballMatchService(FootballMatchRepository footballMatchRepository,
//                                ClubService clubService,
//                                StadiumService stadiumService,
//                                StadiumRepository stadiumRepository,
//                                FootballMatchMessageService footballMatchMessageService) {
//        this.footballMatchRepository = footballMatchRepository;
//        this.clubService = clubService;
//        this.stadiumService = stadiumService;
//        this.stadiumRepository = stadiumRepository;
//        this.footballMatchMessageService = footballMatchMessageService;
//    }
//
//    // ==================== MÉTODOS PRINCIPAIS CRUD ====================
//
//    public FootballMatchDTO createPartida(FootballMatchDTO dto) {
//        try {
//            // Buscar entidades usando métodos auxiliares seguros
//            ClubEntity mandante = getClubByLongId(dto.getHomeClubId());
//            ClubEntity visitante = getClubByLongId(dto.getClubVisitorId());
//            StadiumEntity estadio = getStadiumByLongId(dto.getStadiumId());
//
//            FootballMatch entity = FootballMatchMapper.toEntity(dto, mandante, visitante, estadio);
//            FootballMatch saved = footballMatchRepository.save(entity);
//
//            // Enviar mensagem RabbitMQ se necessário
//            tryToSendMessage(saved, dto);
//
//            return FootballMatchMapper.toDto(saved);
//        } catch (Exception e) {
//            LOG.error("Erro ao criar partida: {}", e.getMessage(), e);
//            throw new RuntimeException("Erro ao criar partida", e);
//        }
//    }
//
//    public FootballMatchDTO updatePartida(Long id, FootballMatchDTO dto) {
//        try {
//            FootballMatch entity = footballMatchRepository.findById(id)
//                    .orElseThrow(() -> new RuntimeException("Partida não encontrada!"));
//
//            // Buscar entidades usando métodos auxiliares seguros
//            ClubEntity mandante = getClubByLongId(dto.getHomeClubId());
//            ClubEntity visitante = getClubByLongId(dto.getClubVisitorId());
//            StadiumEntity estadio = getStadiumByLongId(dto.getStadiumId());
//
//            entity.setHomeClub(mandante);
//            entity.setClubVisitor(visitante);
//            entity.setStadium(estadio);
//            entity.setDateTimeDeparture(dto.getDateTimeDeparture());
//            entity.setHomeTeamGoals(dto.getHomeTeamGoals());
//            entity.setGoalsVisitor(dto.getGoalsVisitor());
//
//            FootballMatch saved = footballMatchRepository.save(entity);
//
//            // Enviar mensagem RabbitMQ se necessário
//            tryToSendMessage(saved, dto);
//
//            return FootballMatchMapper.toDto(saved);
//        } catch (Exception e) {
//            LOG.error("Erro ao atualizar partida: {}", e.getMessage(), e);
//            throw new RuntimeException("Erro ao atualizar partida", e);
//        }
//    }
//
//    public void removerPartida(Long id) {
//        footballMatchRepository.deleteById(id);
//    }
//
//    public FootballMatchDTO findById(Long id) {
//        return footballMatchRepository.findById(id)
//                .map(FootballMatchMapper::toDto)
//                .orElseThrow(() -> new RuntimeException("Partida não encontrada!"));
//    }
//
//    public FootballMatch findEntityById(Long id) {
//        return footballMatchRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Partida não encontrada!"));
//    }
//
//    public Page<FootballMatchDTO> listar(Pageable pageable) {
//        return footballMatchRepository.findAll(pageable).map(FootballMatchMapper::toDto);
//    }
//
//    // ==================== MÉTODOS AUXILIARES PARA BUSCAR ENTIDADES ====================
//
//    /**
//     * Busca clube convertendo Long ID para UUID
//     */
//    private ClubEntity getClubByLongId(Long clubId) {
//        if (clubId == null) {
//            throw new IllegalArgumentException("ID do clube não pode ser nulo");
//        }
//
//        try {
//            UUID clubUuid = createUuidFromLong(clubId, "club");
//            return clubService.findEntityById(clubUuid);
//        } catch (Exception e) {
//            LOG.error("Erro ao buscar clube ID {}: {}", clubId, e.getMessage());
//            throw new RuntimeException("Clube não encontrado: " + clubId, e);
//        }
//    }
//
//    /**
//     * Busca estádio convertendo Long ID para UUID
//     */
//    private StadiumEntity getStadiumByLongId(Long stadiumId) {
//        if (stadiumId == null) {
//            throw new IllegalArgumentException("ID do estádio não pode ser nulo");
//        }
//
//        try {
//            UUID stadiumUuid = createUuidFromLong(stadiumId, "stadium");
//            return stadiumService.findEntityById(stadiumUuid);
//        } catch (Exception e) {
//            LOG.error("Erro ao buscar estádio ID {}: {}", stadiumId, e.getMessage());
//            throw new RuntimeException("Estádio não encontrado: " + stadiumId, e);
//        }
//    }
//
//    // ==================== MÉTODOS DE CONVERSÃO ====================
//
//    /**
//     * Cria UUID determinístico a partir de Long
//     */
//    private UUID createUuidFromLong(Long id, String prefix) {
//        if (id == null) {
//            throw new IllegalArgumentException("ID não pode ser nulo");
//        }
//
//        String seed = prefix + "-" + id;
//        return UUID.nameUUIDFromBytes(seed.getBytes());
//    }
//
//    /**
//     * Converte UUID para Long aproximado
//     */
//    private Long createLongFromUuid(UUID uuid) {
//        if (uuid == null) {
//            return null;
//        }
//
//        return Math.abs((long) uuid.hashCode());
//    }
//
//    // ==================== MÉTODOS PARA RABBITMQ ====================
//
//    /**
//     * Processa mensagem RabbitMQ recebida
//     */
//    public void processMatchInfoMessage(PartidaInformacaoMessage messageDto) {
//        try {
//            validateMessageData(messageDto);
//
//            UUID matchUuid = messageDto.getIdPartida();
//
//            FootballMatch match = buildMatchFromMessage(messageDto);
//            FootballMatch saved = footballMatchRepository.save(match);
//
//            LOG.info("Partida criada da mensagem: id={}, uuid={}",
//                    saved.getId(), matchUuid);
//        } catch (Exception e) {
//            LOG.error("Erro ao processar mensagem: {}", e.getMessage(), e);
//            throw new RuntimeException("Erro ao processar mensagem da partida", e);
//        }
//    }
//
//    /**
//     * Tenta enviar mensagem RabbitMQ sem quebrar a operação principal
//     */
//    private void tryToSendMessage(FootballMatch entity, FootballMatchDTO dto) {
//        try {
//            PartidaInformacaoMessage message = new PartidaInformacaoMessage();
//
//            UUID matchUuid = createUuidFromLong(entity.getId(), "match");
//            message.setIdPartida(matchUuid);
//            message.setDataHoraInicio(dto.getDateTimeDeparture());
//
//            footballMatchMessageService.processarPartidaInformacao(message);
//
//            LOG.debug("Mensagem enviada: matchId={}, uuid={}", entity.getId(), matchUuid);
//        } catch (Exception e) {
//            LOG.warn("Falha ao enviar mensagem (não crítico): {}", e.getMessage());
//        }
//    }
//
//    private FootballMatch buildMatchFromMessage(PartidaInformacaoMessage messageDto) {
//        FootballMatch match = new FootballMatch();
//        match.setDateTimeDeparture(messageDto.getDataHoraInicio());
//        return match;
//    }
//
//    private void validateMessageData(PartidaInformacaoMessage messageDto) {
//        if (messageDto == null) {
//            throw new IllegalArgumentException("Mensagem não pode ser nula");
//        }
//        if (messageDto.getIdPartida() == null) {
//            throw new IllegalArgumentException("ID da partida é obrigatório");
//        }
//        if (messageDto.getDataHoraInicio() == null) {
//            throw new IllegalArgumentException("Data/hora é obrigatória");
//        }
//    }
//
//    // ==================== MÉTODOS PÚBLICOS AUXILIARES ====================
//
//    /**
//     * Busca partida por UUID gerado
//     */
//    public Optional<FootballMatch> findByUuid(UUID uuid) {
//        if (uuid == null) {
//            return Optional.empty();
//        }
//
//        try {
//            Long approximateId = createLongFromUuid(uuid);
//            return footballMatchRepository.findById(approximateId);
//        } catch (Exception e) {
//            LOG.warn("Erro ao buscar por UUID: {}", e.getMessage());
//            return Optional.empty();
//        }
//    }
//
//    /**
//     * Gera UUID público para uma partida
//     */
//    public UUID generateMatchUuid(Long matchId) {
//        return createUuidFromLong(matchId, "match");
//    }
//
//    /**
//     * Busca clube por UUID (método público)
//     */
//    public ClubEntity getClubByUuid(UUID uuid) {
//        return clubService.findEntityById(uuid);
//    }
//
//    /**
//     * Busca estádio por UUID (método público)
//     */
//    public StadiumEntity getStadiumByUuid(UUID uuid) {
//        return stadiumService.findEntityById(uuid);
//    }
//
//    // ==================== VALIDAÇÃO ====================
//
//    private boolean isValidLongId(Long id) {
//        return id != null && id > 0;
//    }
//
//    /**
//     * Cria partida com validação completa
//     */
//    public FootballMatchDTO createPartidaWithValidation(FootballMatchDTO dto) {
//        if (!isValidLongId(dto.getHomeClubId())) {
//            throw new IllegalArgumentException("ID clube mandante inválido: " + dto.getHomeClubId());
//        }
//        if (!isValidLongId(dto.getClubVisitorId())) {
//            throw new IllegalArgumentException("ID clube visitante inválido: " + dto.getClubVisitorId());
//        }
//        if (!isValidLongId(dto.getStadiumId())) {
//            throw new IllegalArgumentException("ID estádio inválido: " + dto.getStadiumId());
//        }
//        if (dto.getDateTimeDeparture() == null) {
//            throw new IllegalArgumentException("Data/hora é obrigatória");
//        }
//
//        return createPartida(dto);
//    }
//
//    /**
//     * Método para debug/teste
//     */
//    public void logConversions(Long id) {
//        UUID clubUuid = createUuidFromLong(id, "club");
//        UUID stadiumUuid = createUuidFromLong(id, "stadium");
//        Long backToLong = createLongFromUuid(clubUuid);
//
//        LOG.info("ID {}: club={}, stadium={}, reverse={}",
//                id, clubUuid, stadiumUuid, backToLong);
//    }
//}
//
//
//
//
//
//
//
//
////package com.neoCamp.footballMatch.service;
////
////import com.neoCamp.footballMatch.dto.FootballMatchDTO;
////import com.neoCamp.footballMatch.dto.rabbitmq.PartidaInformacaoMessage;
////import com.neoCamp.footballMatch.entity.ClubEntity;
////import com.neoCamp.footballMatch.entity.StadiumEntity;
////import com.neoCamp.footballMatch.entity.FootballMatch;
////import com.neoCamp.footballMatch.mapper.FootballMatchMapper;
////import com.neoCamp.footballMatch.repository.FootballMatchRepository;
////import com.neoCamp.footballMatch.repository.StadiumRepository;
////import com.neoCamp.footballMatch.service.FootballMatchMessageService;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.data.domain.Page;
////import org.springframework.data.domain.Pageable;
////import org.springframework.stereotype.Service;
////
////import java.util.UUID;
////
////@Service
////public class FootballMatchService {
////
////    private final FootballMatchRepository footballMatchRepository;
////    private final ClubService clubService;
////    private final StadiumService stadiumService;
////    private final StadiumRepository stadiumRepository;
////    private final FootballMatchMessageService footballMatchMessageService;
////
////    @Autowired
////    public FootballMatchService(FootballMatchRepository footballMatchRepository,
////                                ClubService clubService,
////                                StadiumService stadiumService,
////                                StadiumRepository stadiumRepository,
////                                FootballMatchMessageService footballMatchMessageService) {
////        this.footballMatchRepository = footballMatchRepository;
////        this.clubService = clubService;
////        this.stadiumService = stadiumService;
////        this.stadiumRepository = stadiumRepository;
////        this.footballMatchMessageService = footballMatchMessageService;
////    }
////
////    public FootballMatchDTO createPartida(FootballMatchDTO dto) {
////        ClubEntity mandante = clubService.findEntityById(dto.getHomeClubId());
////        ClubEntity visitante = clubService.findEntityById(dto.getClubVisitorId());
////        StadiumEntity estadio = stadiumService.findEntityById(dto.getStadiumId());
////        FootballMatch entity = FootballMatchMapper.toEntity(dto, mandante, visitante, estadio);
////        // Mapeamento adicional usando FootballMatchMessageService
////        PartidaInformacaoMessage message = new PartidaInformacaoMessage();
////        message.setIdPartida(UUID.nameUUIDFromBytes(entity.getId().toString().getBytes()));
////        message.setDataHoraInicio(dto.getDateTimeDeparture());
////// message.setStatus(dto.getStatus()); // Só se existir esse campo no DTO
////// message.setJogadores(...);
////
////        footballMatchMessageService.processarPartidaInformacao(message);
////        FootballMatch saved = footballMatchRepository.save(entity);
////        return FootballMatchMapper.toDto(saved);
////    }
////
////    public FootballMatchDTO updatePartida(Long id, FootballMatchDTO dto) {
////        FootballMatch entity = footballMatchRepository.findById(id)
////                .orElseThrow(() -> new RuntimeException("Partida não encontrada!"));
////        ClubEntity mandante = clubService.findEntityById(dto.getHomeClubId());
////        ClubEntity visitante = clubService.findEntityById(dto.getClubVisitorId());
////        StadiumEntity estadio = stadiumService.findEntityById(dto.getStadiumId());
////        entity.setHomeClub(mandante);
////        entity.setClubVisitor(visitante);
////        entity.setStadium(estadio);
////        entity.setDateTimeDeparture(dto.getDateTimeDeparture());
////        entity.setHomeTeamGoals(dto.getHomeTeamGoals());
////        entity.setGoalsVisitor(dto.getGoalsVisitor());
////        // Mapeamento adicional usando FootballMatchMessageService
////        PartidaInformacaoMessage message = new PartidaInformacaoMessage();
////        message.setIdPartida(UUID.nameUUIDFromBytes(entity.getId().toString().getBytes()));
////        message.setDataHoraInicio(dto.getDateTimeDeparture());
////// message.setStatus(dto.getStatus()); // Só se existir esse campo no DTO
////// message.setJogadores(...);
////
////
////        footballMatchMessageService.processarPartidaInformacao(message);
////        FootballMatch saved = footballMatchRepository.save(entity);
////        return FootballMatchMapper.toDto(saved);
////    }
////
////    public void removerPartida(Long id) {
////        footballMatchRepository.deleteById(id);
////    }
////
////    public FootballMatchDTO findById(Long id) {
////        return footballMatchRepository.findById(id)
////                .map(FootballMatchMapper::toDto)
////                .orElseThrow(() -> new RuntimeException("Partida não encontrada!"));
////    }
////
////    public FootballMatch findEntityById(Long id) {
////        return footballMatchRepository.findById(id)
////                .orElseThrow(() -> new RuntimeException("Partida não encontrada!"));
////    }
////
////    public Page<FootballMatchDTO> listar(Pageable pageable) {
////        return footballMatchRepository.findAll(pageable).map(FootballMatchMapper::toDto);
////    }
////
////}
