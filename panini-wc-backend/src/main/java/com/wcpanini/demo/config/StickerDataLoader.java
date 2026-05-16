package com.wcpanini.demo.config;



import com.wcpanini.demo.entities.Sticker;
import com.wcpanini.demo.repositories.StickerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class StickerDataLoader implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(StickerDataLoader.class);
    private static final String CSV_CLASSPATH = "panini.csv";

    private final StickerRepository stickerRepository;

    public StickerDataLoader(StickerRepository stickerRepository) {
        this.stickerRepository = stickerRepository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        long count = stickerRepository.count();

        if (count > 0) {
            log.info("Table has {} sticker(s) — rewriting panini.csv...", count);
            exportToCsv(stickerRepository.findAll());
        } else {
            log.info("Table is empty — importing from {}...", CSV_CLASSPATH);
            List<Sticker> stickers = parseCsv();
            stickerRepository.saveAll(stickers);
            log.info("Inserted {} stickers.", stickers.size());
        }
    }

    // ─────────────────────────────────────────────────────────────
    // Export: overwrite the same panini.csv in resources/
    // ─────────────────────────────────────────────────────────────
    private void exportToCsv(List<Sticker> stickers) throws Exception {
        Path csvFile = resolveStichersCsvPath();

        try (BufferedWriter writer = Files.newBufferedWriter(csvFile, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {

            writer.write("name,type,nationality,place");
            writer.newLine();

            for (Sticker s : stickers) {
                writer.write(String.join(",",
                        escapeCsv(s.getName()),
                        escapeCsv(s.getType()),
                        s.getNationality() != null ? escapeCsv(s.getNationality()) : "null",
                        escapeCsv(s.getPlace())
                ));
                writer.newLine();
            }
        }

        log.info("Rewrote {} rows → {}", stickers.size(), csvFile.toAbsolutePath());
    }

    // ─────────────────────────────────────────────────────────────
    // Import: parse panini.csv from classpath
    // ─────────────────────────────────────────────────────────────
    private List<Sticker> parseCsv() throws Exception {
        List<Sticker> stickers = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new ClassPathResource(CSV_CLASSPATH).getInputStream(),
                        StandardCharsets.UTF_8))) {

            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {
                if (firstLine) { firstLine = false; continue; }
                if (line.isBlank()) continue;

                String[] cols = line.split(",", 4);
                if (cols.length < 4) continue;

                String name        = cols[0].trim();
                String type        = cols[1].trim();
                String nationality = cols[2].trim().equalsIgnoreCase("null") ? null : cols[2].trim();
                String place       = cols[3].trim();

                stickers.add(new Sticker(name, type, nationality, place));
            }
        }

        return stickers;
    }

    // ─────────────────────────────────────────────────────────────
    // Resolve the actual path of panini.csv on disk
    // ─────────────────────────────────────────────────────────────

    /**
     * In dev (IDE / Maven): resolves to src/main/resources/panini.csv
     * In JAR: falls back to the file next to the running jar (exported there once)
     */
    private Path resolveStichersCsvPath() throws IOException {
        try {
            File classpathRoot = ResourceUtils.getFile("classpath:");
            // target/classes → target/ → project root → src/main/resources/panini.csv
            Path devPath = classpathRoot.toPath()
                    .getParent()
                    .getParent()
                    .resolve("src/main/resources/panini.csv");

            if (Files.exists(devPath)) {
                return devPath;
            }
        } catch (FileNotFoundException ignored) {}

        // JAR fallback: write alongside the jar
        Path fallback = Paths.get("panini.csv");
        Files.createDirectories(fallback.getParent());
        return fallback;
    }

    private String escapeCsv(String value) {
        if (value == null) return "";
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }
}