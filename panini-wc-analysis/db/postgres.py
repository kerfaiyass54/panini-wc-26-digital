import pandas as pd
import psycopg2

from config.settings import POSTGRES_CONFIG

def get_stickers_dataframe(email):

    connection = psycopg2.connect(
        host=POSTGRES_CONFIG["host"],
        port=POSTGRES_CONFIG["port"],
        database=POSTGRES_CONFIG["database"],
        user=POSTGRES_CONFIG["user"],
        password=POSTGRES_CONFIG["password"],
    )

    query = """
            SELECT s.name, \
                   s.type, \
                   s.nationality, \
                   s.place
            FROM owning o
                     INNER JOIN stickers s
                                ON s.place = o.code
            WHERE o.email = %s
            ORDER BY s.place ASC \
            """

    dataframe = pd.read_sql(
        query,
        connection,
        params=[email]
    )

    connection.close()

    return dataframe