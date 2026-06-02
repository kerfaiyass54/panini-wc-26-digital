from  sqlalchemy.orm import Session

from models.duplicate import Duplicate


class DuplicateRepository:

    @staticmethod
    def find_by_email(
            db: Session,
            email: str
    ):

        return db.query(
            Duplicate
        ).filter(
            Duplicate.email == email
        ).all()