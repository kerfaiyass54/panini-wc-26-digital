from repositories.sticker_repository import StickerRepository

from services.nation_score_service import NationScoreService
from services.player_value_service import PlayerValueService

from repositories.owning_repository import OwningRepository
from repositories.duplicate_repository import DuplicateRepository


class CandidateService:

    def __init__(self, db):

        self.db = db

        self.nation_service = NationScoreService(
            db
        )

    def score_sticker(
            self,
            target_user: str,
            code: str
    ):

        sticker = StickerRepository.find_by_code(
            self.db,
            code
        )

        if not sticker:
            return 0

        rating = PlayerValueService.get_value(
            code
        )

        completion = (
            self.nation_service.completion(
                target_user,
                sticker.nationality
            )
        )

        score = 0

        # player quality
        score += rating * 5

        # nation completion
        score += completion * 200

        # almost completed nation bonus
        if completion >= 0.90:
            score += 300

        elif completion >= 0.80:
            score += 200

        elif completion >= 0.70:
            score += 100

        return round(score, 2)

    def tradable_for_user(
            self,
            source_user: str,
            target_user: str
    ):

        duplicates = (
            DuplicateRepository.find_by_email(
                self.db,
                source_user
            )
        )

        target_owned = {
            o.code
            for o in OwningRepository.find_by_email(
                self.db,
                target_user
            )
        }

        candidates = []

        for duplicate in duplicates:

            if duplicate.code not in target_owned:

                candidates.append(
                    duplicate.code
                )

        return candidates