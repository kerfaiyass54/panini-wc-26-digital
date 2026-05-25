from db.postgres import (
    get_stickers_dataframe
)

from kafkaProducer.producer import publish

def process_nationality_stats(email):

    dataframe = get_stickers_dataframe(email)

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

        "email":
            email,

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