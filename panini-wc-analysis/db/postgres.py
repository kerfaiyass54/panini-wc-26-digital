import pandas as pd
import psycopg2

from config.settings import POSTGRES_CONFIG

def get_stickers_dataframe():

    connection = psycopg2.connect(
        host=POSTGRES_CONFIG["host"],
        port=POSTGRES_CONFIG["port"],
        database=POSTGRES_CONFIG["database"],
        user=POSTGRES_CONFIG["user"],
        password=POSTGRES_CONFIG["password"],
    )

    query = "SELECT * FROM stickers"

    dataframe = pd.read_sql(
        query,
        connection
    )

    connection.close()

    return dataframe