// StickerService.java
package com.wcpanini.demo.services;

import com.wcpanini.demo.dtos.StickerSimpleResponse;
import com.wcpanini.demo.entities.Duplicate;
import com.wcpanini.demo.entities.Owning;
import com.wcpanini.demo.entities.Sticker;
import com.wcpanini.demo.repositories.DuplicateRepository;
import com.wcpanini.demo.repositories.OwningRepository;
import com.wcpanini.demo.repositories.StickerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
public class StickerService {

    private final OwningRepository owningRepository;
    private final DuplicateRepository duplicateRepository;
    private final KafkaTemplate kafkaTemplate;
    private final StickerRepository stickerRepository;

    public StickerService(
            OwningRepository owningRepository,
            DuplicateRepository duplicateRepository,
            KafkaTemplate kafkaTemplate,
            StickerRepository stickerRepository
    ) {
        this.owningRepository = owningRepository;
        this.duplicateRepository = duplicateRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.stickerRepository = stickerRepository;
    }

    // add new sticker to owning
    public Owning addSticker(String email, String code) {

        if (owningRepository.existsByEmailAndCode(email, code)) {
            throw new RuntimeException("Sticker already exists");
        }

        Owning owning = new Owning(email, code);
        kafkaTemplate.send(
                "stickers-refresh-topic",
                "refresh for: " + email
        );

        return owningRepository.save(owning);
    }

    // check if user owns sticker
    public boolean hasSticker(String email, String place) {
        return owningRepository.existsByEmailAndCode(email, place);
    }

    // duplicate sticker => create with number = 2
    public Duplicate duplicateSticker(String email, String place) {

        if (!hasSticker(email, place)) {
            throw new RuntimeException("User does not own this sticker");
        }

        Duplicate duplicate = duplicateRepository
                .findByEmailAndCode(email, place)
                .orElse(null);

        if (duplicate != null) {
            duplicate.setNumber(duplicate.getNumber() + 1);
            return duplicateRepository.save(duplicate);
        }

        Duplicate newDuplicate = new Duplicate(email, place, 2);

        return duplicateRepository.save(newDuplicate);
    }

    // increase duplicates by 1
    public Duplicate increaseDuplicate(String email, String place) {

        Duplicate duplicate = duplicateRepository
                .findByEmailAndCode(email, place)
                .orElseThrow(() ->
                        new RuntimeException("Duplicate not found")
                );

        duplicate.setNumber(duplicate.getNumber() + 1);

        return duplicateRepository.save(duplicate);
    }
    
    public StickerSimpleResponse convertStickerToDTO(Sticker sticker) {
        StickerSimpleResponse stickerDTO = new StickerSimpleResponse();
        stickerDTO.setName(sticker.getName());
        stickerDTO.setPlace(sticker.getPlace());
        stickerDTO.setNationality(sticker.getNationality());
        return stickerDTO;
        
    }
    
    public StickerSimpleResponse getStickerInfo(String code){
        return convertStickerToDTO(stickerRepository.findStickerByPlace(code));
    }

    public Page<StickerSimpleResponse> getOwnings(
            String email,
            Pageable pageable
    ) {
        return owningRepository.findAllByEmail(email, pageable)
                .map(owning -> {
                    StickerSimpleResponse sticker = getStickerInfo(owning.getCode());
                    return new StickerSimpleResponse(
                            sticker.getName(),
                            sticker.getNationality(),
                            sticker.getPlace()
                    );
                });
    }

    public Page<Duplicate> getDuplicates(
            String email,
            Pageable pageable
    ) {
        return duplicateRepository.findAllByEmail(email, pageable);
    }

    public void removeDuplicate(String email, String place) {
        duplicateRepository.deleteByEmailAndCode(email, place);
    }
}