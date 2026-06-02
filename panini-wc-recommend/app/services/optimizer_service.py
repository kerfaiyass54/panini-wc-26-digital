from services.candidate_service import CandidateService

from repositories.sticker_repository import StickerRepository

from services.player_value_service import (
    PlayerValueService
)


class OptimizerService:

    def __init__(self, db):

        self.db = db

        self.candidate_service = CandidateService(
            db
        )

    def top_stickers(
            self,
            source_user: str,
            target_user: str,
            limit: int = 10
    ):

        candidates = (
            self.candidate_service.tradable_for_user(
                source_user,
                target_user
            )
        )

        scored = []

        for code in candidates:

            sticker = StickerRepository.find_by_code(
                self.db,
                code
            )

            if not sticker:
                continue

            score = (
                self.candidate_service.score_sticker(
                    target_user,
                    code
                )
            )

            scored.append(
                {
                    "code": code,
                    "name": sticker.name,
                    "nationality": sticker.nationality,
                    "rating": PlayerValueService.get_value(
                        code
                    ),
                    "score": score
                }
            )

        scored.sort(
            key=lambda x: x["score"],
            reverse=True
        )

        return scored[:limit]