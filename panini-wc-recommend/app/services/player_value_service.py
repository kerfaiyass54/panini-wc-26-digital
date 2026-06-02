from data.player_tiers import PLAYER_TIERS


class PlayerValueService:

    @staticmethod
    def get_value(code: str) -> int:

        return PLAYER_TIERS.get(code, 70)