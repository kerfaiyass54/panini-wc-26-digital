from services.trade_match_service import (
    TradeMatchService
)


class TradePackageService:

    def __init__(self, db):

        self.matcher = TradeMatchService(
            db
        )

    def build_package(
            self,
            user1,
            user2,
            max_swaps=5
    ):

        candidates = (
            self.matcher.generate(
                user1,
                user2
            )
        )

        used_user1 = set()
        used_user2 = set()

        chosen = []

        total_score = 0

        for trade in candidates:

            if trade["fairness"] < 90:
                continue

            give = trade["user1_gives"]["code"]
            receive = trade["user2_gives"]["code"]

            if give in used_user1:
                continue

            if receive in used_user2:
                continue

            chosen.append(
                trade
            )

            used_user1.add(
                give
            )

            used_user2.add(
                receive
            )

            total_score += (
                trade["score"]
            )

            if len(chosen) >= max_swaps:
                break

        fairness = 0

        if chosen:
            fairness = (
                    sum(
                        t["fairness"]
                        for t in chosen
                    )
                    /
                    len(chosen)
            )

        return {
            "total_score":
                round(
                    total_score,
                    2
                ),

            "fairness_score":
                round(
                    fairness,
                    2
                ),

            "swaps":
                chosen
        }