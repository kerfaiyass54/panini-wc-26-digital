package com.wcpanini.demo.services;

import com.wcpanini.demo.dtos.OwnedPlayerDTO;
import com.wcpanini.demo.entities.Owning;
import com.wcpanini.demo.entities.Sticker;
import com.wcpanini.demo.repositories.OwningRepository;
import com.wcpanini.demo.repositories.StickerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OwnedPlayerService {

    private final OwningRepository owningRepository;
    private final StickerRepository stickerRepository;

    public List<OwnedPlayerDTO> getOwnedPlayers(String email) {
        List<String> ownedPlaces = owningRepository.findByEmail(email)
                .stream()
                .map(Owning::getCode)
                .toList();

        if (ownedPlaces.isEmpty()) {
            return List.of();
        }

        return stickerRepository
                .findByPlaceInAndTypeIgnoreCase(ownedPlaces, "player")
                .stream()
                .map(this::toOwnedPlayer)
                .filter(player -> player.position() != null)
                .toList();
    }

    private OwnedPlayerDTO toOwnedPlayer(Sticker sticker) {
        String place = sticker.getPlace();
        String suffix = place == null ? "" : place.replaceFirst("^[A-Za-z]+", "");

        final int number;
        try {
            number = Integer.parseInt(suffix);
        } catch (NumberFormatException exception) {
            return new OwnedPlayerDTO(
                    sticker.getId(), sticker.getPlace(), sticker.getName(),
                    sticker.getNationality(), null
            );
        }

        String position = switch (number) {
            case 1 -> "GOALKEEPER";
            case 2, 3, 4, 5 -> "DEFENDER";
            case 6, 7, 8 -> "MIDFIELDER";
            case 9, 10, 11 -> "STRIKER";
            default -> null;
        };

        return new OwnedPlayerDTO(
                sticker.getId(),
                sticker.getPlace(),
                sticker.getName(),
                sticker.getNationality(),
                position
        );
    }
}
