from db.postgres import (
    get_stickers_dataframe
)

from kafkaProducer.producer import publish

def process_nations_stats(email):

    dataframe = get_stickers_dataframe(email)

    result = (
        dataframe["nationality"]
        .value_counts()
        .to_dict()
    )

    payload = {
        "email": email,
        "data": result
    }

    publish(
        "stickers-nations-topic",
        payload
    )