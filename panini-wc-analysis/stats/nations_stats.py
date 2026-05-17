from db.postgres import (
    get_stickers_dataframe
)

from kafkaProducer.producer import publish

def process_nations_stats():

    dataframe = get_stickers_dataframe()

    result = (
        dataframe["nationality"]
        .value_counts()
        .to_dict()
    )

    publish(
        "stickers-nations-topic",
        result
    )