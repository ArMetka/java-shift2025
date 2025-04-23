package ru.shift.highscore.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.shift.common.dto.HighScore;
import ru.shift.common.enums.GameType;
import ru.shift.highscore.exception.InvalidHighScoreFileFormat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumMap;

public class HighScoreRepository {
    private static final Logger log = LogManager.getLogger(HighScoreRepository.class);

    private final Path filepath;
    private final EnumMap<GameType, HighScore> highScores;

    private boolean isEnabled = false;

    public HighScoreRepository(String filepath) {
        this.filepath = Paths.get(filepath).toAbsolutePath();
        highScores = new EnumMap<>(GameType.class);

        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(this.filepath))) {
            for (var type : GameType.values()) {
                var inType = (GameType) in.readObject();
                if (inType != type) {
                    throw new InvalidHighScoreFileFormat("provided high score file corrupted: " + this.filepath);
                }
                var score = (HighScore) in.readObject();
                highScores.put(type, score);
            }
            log.info("high scores file {} successfully loaded", this.filepath);
            isEnabled = true;
        } catch (NoSuchFileException e) {
            log.info("no high scores file found, creating new at {}", this.filepath);
            createEmptyHighScores();
        } catch (ClassNotFoundException | InvalidHighScoreFileFormat e) {
            log.error("provided high scores file {} is corrupted", this.filepath);
        } catch (IOException e) {
            log.fatal("IOException while reading file {}", this.filepath, e);
        }
    }

    public void saveNewHighScore(HighScore highScore) {
        if (!isEnabled) {
            return;
        }

        highScores.put(highScore.gameType(), highScore);
        writeHighScores();
    }

    public HighScore findByGameType(GameType gameType) {
        if (!isEnabled) {
            return HighScore.emptyScore(gameType);
        }

        return highScores.get(gameType);
    }

    private void createEmptyHighScores() {
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(filepath))) {
            for (var type : GameType.values()) {
                var filler = HighScore.emptyScore(type);
                highScores.put(type, filler);
                out.writeObject(type);
                out.writeObject(filler);
            }
            isEnabled = true;
        } catch (IOException e) {
            log.fatal("IOException while writing to file {}", filepath, e);
            isEnabled = false;
        }
    }

    private void writeHighScores() {
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(filepath))) {
            for (var type : GameType.values()) {
                out.writeObject(type);
                out.writeObject(highScores.get(type));
            }
            isEnabled = true;
        } catch (IOException e) {
            log.fatal("IOException while writing to file {}", filepath, e);
            isEnabled = false;
        }
    }
}
