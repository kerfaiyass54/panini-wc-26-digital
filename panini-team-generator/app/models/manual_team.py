from pydantic import BaseModel


class ManualTeamRequest(BaseModel):

    email: str

    team_name: str

    player_ids: list[int]