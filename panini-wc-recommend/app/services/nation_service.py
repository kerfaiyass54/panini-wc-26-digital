class NationService:

    @staticmethod
    def get_country(code: str) -> str:
        return ''.join(
            [
                c
                for c in code
                if not c.isdigit()
            ]
        )