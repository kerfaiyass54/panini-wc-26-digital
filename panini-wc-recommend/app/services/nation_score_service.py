from  sqlalchemy.orm import Session

from repositories.sticker_repository import StickerRepository
from repositories.owning_repository import OwningRepository


class NationScoreService:

    def __init__(self, db: Session):

        self.db = db

    def completion(
            self,
            email: str,
            nationality: str
    ) -> float:

        stickers = StickerRepository.find_by_nationality(
            self.db,
            nationality
        )

        total = len(stickers)

        if total == 0:
            return 0

        owned_codes = {
            o.code
            for o in OwningRepository.find_by_email(
                self.db,
                email
            )
        }

        owned_count = sum(
            1
            for sticker in stickers
            if sticker.place in owned_codes
        )

        return owned_count / total