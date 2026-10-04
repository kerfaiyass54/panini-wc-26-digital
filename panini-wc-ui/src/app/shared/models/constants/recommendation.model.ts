export interface StickerTrade {
  code: string;
  name: string;
  rating: number;
}

export interface SwapRecommendation {
  score: number;
  fairness: number;

  user1_gives: StickerTrade;
  user2_gives: StickerTrade;
}

export interface RecommendationResponse {
  total_score: number;
  fairness_score: number;
  swaps: SwapRecommendation[];
}
