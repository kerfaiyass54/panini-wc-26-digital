from db.postgres import (
    get_stickers_dataframe
)

from kafkaProducer.producer import publish

from constants.worldcup import GROUPS

def process_groups_stats():

    dataframe = get_stickers_dataframe()

    # CLEAN VALUES

    dataframe["nationality"] = (
        dataframe["nationality"]
        .fillna("")
        .astype(str)
        .str.strip()
        .str.lower()
    )

    stats = {}

    for group, nations in GROUPS.items():

        normalized_nations = [
            nation.strip().lower()
            for nation in nations
        ]

        count = dataframe[
            dataframe["nationality"]
            .isin(normalized_nations)
        ].shape[0]

        stats[group] = int(count)

    publish(
        "stickers-groups-topic",
        stats
    )