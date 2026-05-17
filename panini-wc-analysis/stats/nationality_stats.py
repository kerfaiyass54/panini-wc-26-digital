from db.postgres import (
    get_stickers_dataframe
)

from kafkaProducer.producer import publish

def process_nationality_stats():

    dataframe = get_stickers_dataframe()

    dataframe = dataframe.dropna(
        subset=["nationality"]
    )

    result = (
        dataframe["nationality"]
        .value_counts()
        .to_dict()
    )

    most_common = max(
        result,
        key=result.get
    )

    payload = {

        "most_nationality":
            most_common,

        "count":
            result[most_common],

        "all":
            result
    }

    publish(
        "stickers-nationality-topic",
        payload
    )