from models.sticker import Base

from  sqlalchemy import Column
from  sqlalchemy import BigInteger
from  sqlalchemy import String

class Owning(Base):

    __tablename__ = "owning"

    id = Column(
        BigInteger,
        primary_key=True
    )

    email = Column(String)

    code = Column(String)