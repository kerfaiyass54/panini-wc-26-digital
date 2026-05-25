from db.postgres import (
    get_stickers_dataframe
)

from kafkaProducer.producer import publish

from constants.worldcup import CONTINENTS

def process_continent_stats(email):

    dataframe = get_stickers_dataframe(email)

    dataframe["nationality"] = (
        dataframe["nationality"]
        .fillna("")
        .astype(str)
        .str.strip()
        .str.lower()
    )

    stats = {}

    for continent, nations in CONTINENTS.items():

        normalized_nations = [
            nation.strip().lower()
            for nation in nations
        ]

        count = dataframe[
            dataframe["nationality"]
            .isin(normalized_nations)
        ].shape[0]

        stats[continent] = int(count)

    payload = {
        "email": email,
        "data": stats
    }

    publish(
        "stickers-continents-topic",
        payload
    )