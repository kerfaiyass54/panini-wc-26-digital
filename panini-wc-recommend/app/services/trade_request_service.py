from collections import defaultdict

from repositories.duplicate_repository import (
    DuplicateRepository
)

from repositories.owning_repository import (
    OwningRepository
)

from repositories.sticker_repository import (
    StickerRepository
)

from services.candidate_service import (
    CandidateService
)

from services.player_value_service import (
    PlayerValueService
)


class TradeRequestService:

    def __init__(self, db):

        self.db = db

        self.candidate_service = CandidateService(
            db
        )

    def generate(
            self,
            email: str
    ):

        duplicates = (
            DuplicateRepository.find_by_email(
                self.db,
                email
            )
        )

        owned = (
            OwningRepository.find_by_email(
                self.db,
                email
            )
        )

        owned_codes = {
            o.code
            for o in owned
        }

        #
        # GIVE PLAYERS
        #

        give_players = []

        give_countries = defaultdict(int)

        for duplicate in duplicates:

            sticker = (
                StickerRepository.find_by_code(
                    self.db,
                    duplicate.code
                )
            )

            if not sticker:
                continue

            rating = (
                PlayerValueService.get_value(
                    duplicate.code
                )
            )

            score = (
                duplicate.number * 100
                + rating
            )

            give_players.append(
                {
                    "code": duplicate.code,
                    "name": sticker.name,
                    "nationality": sticker.nationality,
                    "rating": rating,
                    "score": score
                }
            )

            give_countries[
                sticker.nationality
            ] += duplicate.number

        give_players.sort(
            key=lambda x: x["score"],
            reverse=True
        )

        top_give_players = (
            give_players[:5]
        )

        top_give_countries = [

            nation

            for nation, _ in

            sorted(
                give_countries.items(),
                key=lambda x: x[1],
                reverse=True
            )[:3]
        ]

        #
        # WANT PLAYERS
        #

        all_stickers = (
            StickerRepository.find_all(
                self.db
            )
        )

        want_players = []

        want_countries = defaultdict(float)

        for sticker in all_stickers:

            if sticker.place in owned_codes:
                continue

            score = (
                self.candidate_service
                .score_sticker(
                    email,
                    sticker.place
                )
            )

            rating = (
                PlayerValueService.get_value(
                    sticker.place
                )
            )

            want_players.append(
                {
                    "code": sticker.place,
                    "name": sticker.name,
                    "nationality": sticker.nationality,
                    "rating": rating,
                    "score": score
                }
            )

            want_countries[
                sticker.nationality
            ] += score

        want_players.sort(
            key=lambda x: x["score"],
            reverse=True
        )

        top_want_players = (
            want_players[:5]
        )

        top_want_countries = [

            nation

            for nation, _ in

            sorted(
                want_countries.items(),
                key=lambda x: x[1],
                reverse=True
            )[:3]
        ]

        return {

            "give": {

                "players":
                    top_give_players,

                "countries":
                    top_give_countries
            },

            "want": {

                "players":
                    top_want_players,

                "countries":
                    top_want_countries
            }
        }