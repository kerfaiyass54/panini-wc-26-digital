from  sqlalchemy.orm import DeclarativeBase
from  sqlalchemy import Column
from  sqlalchemy import BigInteger
from  sqlalchemy import String

class Base(DeclarativeBase):
    pass


class Sticker(Base):

    __tablename__ = "stickers"

    id = Column(
        BigInteger,
        primary_key=True
    )

    name = Column(String)

    type = Column(String)

    nationality = Column(String)

    place = Column(String)