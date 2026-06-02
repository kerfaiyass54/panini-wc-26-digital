from services.candidate_service import CandidateService
from services.player_value_service import (
    PlayerValueService
)

from repositories.sticker_repository import (
    StickerRepository
)


class TradeMatchService:

    def __init__(self, db):

        self.db = db

        self.candidate_service = CandidateService(
            db
        )

    def generate(
            self,
            user1: str,
            user2: str
    ):

        user1_candidates = (
            self.candidate_service
            .tradable_for_user(
                user1,
                user2
            )
        )

        user2_candidates = (
            self.candidate_service
            .tradable_for_user(
                user2,
                user1
            )
        )

        trades = []

        for give_code in user1_candidates:

            give_rating = (
                PlayerValueService
                .get_value(
                    give_code
                )
            )

            give_score = (
                self.candidate_service
                .score_sticker(
                    user2,
                    give_code
                )
            )

            give_sticker = (
                StickerRepository
                .find_by_code(
                    self.db,
                    give_code
                )
            )

            for receive_code in user2_candidates:

                receive_rating = (
                    PlayerValueService
                    .get_value(
                        receive_code
                    )
                )

                receive_score = (
                    self.candidate_service
                    .score_sticker(
                        user1,
                        receive_code
                    )
                )

                receive_sticker = (
                    StickerRepository
                    .find_by_code(
                        self.db,
                        receive_code
                    )
                )

                fairness = (
                    100 -
                    abs(
                        give_rating -
                        receive_rating
                    )
                )

                trade_value = (
                    give_score +
                    receive_score +
                    fairness * 5
                )

                trades.append(
                    {
                        "score": round(
                            trade_value,
                            2
                        ),

                        "fairness": fairness,

                        "user1_gives": {
                            "code":
                                give_code,
                            "name":
                                give_sticker.name,
                            "rating":
                                give_rating
                        },

                        "user2_gives": {
                            "code":
                                receive_code,
                            "name":
                                receive_sticker.name,
                            "rating":
                                receive_rating
                        }
                    }
                )

        trades.sort(
            key=lambda x: x["score"],
            reverse=True
        )

        return trades[:25]