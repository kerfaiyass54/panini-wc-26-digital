package com.wcpanini.demo.services;

import com.wcpanini.demo.dtos.StickerDTO;
import com.wcpanini.demo.dtos.StickerStatsDTO;
import com.wcpanini.demo.entities.Sticker;
import com.wcpanini.demo.repositories.StickerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class StickerService {

    private final StickerRepository stickerRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public StickerService(StickerRepository stickerRepository, KafkaTemplate<String, String> kafkaTemplate) {
        this.stickerRepository = stickerRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    // ──────────────────────────────────────────────
    // READ — paginated
    // ──────────────────────────────────────────────

    /**
     * Returns a paginated list of all stickers.
     *
     * @param page page index (0-based)
     * @param size number of items per page
     * @param sortBy field to sort by (default: "id")
     */
    public Page<StickerDTO> findAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return stickerRepository.findAll(pageable).map(this::toDTO);
    }

    // ──────────────────────────────────────────────
    // CREATE
    // ──────────────────────────────────────────────

    @Transactional
    public StickerDTO create(StickerDTO dto) {

        Sticker sticker = new Sticker(
                dto.name(),
                dto.type(),
                dto.nationality(),
                dto.place()
        );

        StickerDTO saved =
                toDTO(stickerRepository.save(sticker));

        kafkaTemplate.send(
                "stickers-refresh-topic",
                "refresh"
        );

        return saved;
    }

    // ──────────────────────────────────────────────
    // UPDATE
    // ──────────────────────────────────────────────

    @Transactional
    public StickerDTO update(Long id, StickerDTO dto) {
        Sticker sticker = stickerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sticker not found: " + id));

        sticker.setName(dto.name());
        sticker.setType(dto.type());
        sticker.setNationality(dto.nationality());
        sticker.setPlace(dto.place());
        kafkaTemplate.send(
                "stickers-refresh-topic",
                "refresh"
        );

        return toDTO(stickerRepository.save(sticker));
    }

    // ──────────────────────────────────────────────
    // DELETE
    // ──────────────────────────────────────────────

    @Transactional
    public void delete(Long id) {
        if (!stickerRepository.existsById(id)) {
            throw new EntityNotFoundException("Sticker not found: " + id);
        }
        kafkaTemplate.send(
                "stickers-refresh-topic",
                "refresh"
        );
        stickerRepository.deleteById(id);
    }

    // ──────────────────────────────────────────────
    // Mapper
    // ──────────────────────────────────────────────

    private StickerDTO toDTO(Sticker s) {
        return new StickerDTO(s.getId(), s.getName(), s.getType(), s.getNationality(), s.getPlace());
    }

    public StickerStatsDTO getStats() {

        long total   = stickerRepository.count();

        long logos   = stickerRepository.countByTypeIgnoreCase("logo");

        long intros  = stickerRepository.countByTypeIgnoreCase("intro");

        long players = stickerRepository.countByTypeIgnoreCase("player");

        return new StickerStatsDTO(
                total,
                logos,
                intros,
                players
        );
    }

    public Page<StickerDTO> search(
            String query,
            int page,
            int size
    ) {

        Pageable pageable =
                PageRequest.of(page, size);

        return stickerRepository
                .findByNameContainingIgnoreCaseOrTypeContainingIgnoreCaseOrNationalityContainingIgnoreCaseOrPlaceContainingIgnoreCase(
                        query,
                        query,
                        query,
                        query,
                        pageable
                )
                .map(this::toDTO);
    }

    public List<String> getLogoNationalities() {

        return stickerRepository.findAll()
                .stream()
                .filter(sticker ->
                        sticker.getType()
                                .equalsIgnoreCase("logo")
                )
                .map(Sticker::getNationality)
                .toList();
    }
}