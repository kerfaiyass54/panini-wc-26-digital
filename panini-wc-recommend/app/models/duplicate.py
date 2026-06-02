from models.sticker import Base

from  sqlalchemy import Column
from  sqlalchemy import BigInteger
from  sqlalchemy import Integer
from  sqlalchemy import String

class Duplicate(Base):

    __tablename__ = "duplicates"

    id = Column(
        BigInteger,
        primary_key=True
    )

    email = Column(String)

    code = Column(String)

    number = Column(Integer)