from stats.nationality_stats import (
    process_nationality_stats
)

from stats.nations_stats import (
    process_nations_stats
)

from stats.groups_stats import (
    process_groups_stats
)

from stats.continent_stats import (
    process_continent_stats
)

if __name__ == "__main__":

    process_nationality_stats()

    process_nations_stats()

    process_groups_stats()

    process_continent_stats()

    print(
        "all analytics sent to kafkaProducer"
    )