from  sqlalchemy.orm import Session

from models.sticker import Sticker


class StickerRepository:

    @staticmethod
    def find_all(db: Session):

        return db.query(
            Sticker
        ).all()

    @staticmethod
    def find_by_code(
            db: Session,
            code: str
    ):

        return db.query(
            Sticker
        ).filter(
            Sticker.place == code
        ).first()

    @staticmethod
    def find_by_nationality(
            db: Session,
            nationality: str
    ):

        return db.query(
            Sticker
        ).filter(
            Sticker.nationality == nationality
        ).all()