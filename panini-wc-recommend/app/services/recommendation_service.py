from  services.trade_package_service import (
    TradePackageService
)


class RecommendationService:

    def __init__(self, db):

        self.package_service = (
            TradePackageService(
                db
            )
        )

    def recommend(
            self,
            user1,
            user2
    ):

        return (
            self.package_service
            .build_package(
                user1,
                user2
            )
        )