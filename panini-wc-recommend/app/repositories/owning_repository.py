from  sqlalchemy.orm import Session

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