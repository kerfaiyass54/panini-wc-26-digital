class TradeBalanceService:

    @staticmethod
    def balance_score(
            give,
            receive
    ):

        give_value = sum(
            item["rating"]
            for item in give
        )

        receive_value = sum(
            item["rating"]
            for item in receive
        )

        if give_value == 0 and receive_value == 0:
            return 100

        diff = abs(
            give_value -
            receive_value
        )

        max_value = max(
            give_value,
            receive_value
        )

        fairness = (
            1 - diff / max_value
        )

        return round(
            fairness * 100,
            2
        )