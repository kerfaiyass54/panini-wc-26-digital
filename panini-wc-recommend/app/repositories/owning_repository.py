from sqlalchemy.orm import Session

from models.owning import Owning


class OwningRepository:

    @staticmethod
    def find_by_email(
            db: Session,
            email: str
    ):

        return db.query(
            Owning
        ).filter(
            Owning.email == email
        ).all()

    @staticmethod
    def find_codes_by_email(
            db: Session,
            email: str
    ):

        return [
            owning.code
            for owning in db.query(
                Owning
            ).filter(
                Owning.email == email
            ).all()
        ]