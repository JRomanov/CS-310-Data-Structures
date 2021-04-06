package edu.sdsu.cs;

//CS-310


import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class App {
    public static void main(String[] args) throws IOException {
        File startDir;
        List<File> filesFound = new ArrayList<>();

        if (args.length == 0)
            startDir = new File(System.getProperty("user.dir"));
        else
            startDir = new File(args[0]);

        if (!startDir.isDirectory()) {
            System.out.println("Invalid Argument. Not a directory.");
            System.exit(1);
        }

        filesFound = discoverFiles(startDir, filesFound);

        for (File fileToProcess : filesFound) {
            List<String> linesInFile = Files.readAllLines(Paths.get(fileToProcess.toString()),
                    Charset.defaultCharset());
            List<String> statList = new ArrayList<>();

            if (linesInFile.size() == 0) {
                System.out.printf("\n Empty: %s", fileToProcess.getName());
                continue;
            }

            statList.add(longestLine(linesInFile));
            statList.add(averageLineLength(linesInFile));
            statList.add(uniqueCaseSensitiveTokens(linesInFile));
            statList.add(uniqueCaseInsensitiveTokens(linesInFile));
            statList.add(numberOfTokens(linesInFile).toString());
            statList.add(mostFrequentTokens(linesInFile));
            statList.add(tokenFrequencies(linesInFile));

            statListWrite(fileToProcess, statList);
        }
    }

    private static void statListWrite(File fileToProcess, List<String> statList) throws IOException {
        String filename = String.format("%s\\%s.stats", fileToProcess.getParent(),
                new File(fileToProcess.toString()).getName());
        Files.write(Paths.get(filename), statList, Charset.defaultCharset());
    }

    private static List<File> discoverFiles(File dir, List<File> matchingFiles) {
        ArrayList<File> tempMatchingFiles;

        tempMatchingFiles = new ArrayList<>(Arrays.asList(Objects.requireNonNull(dir.listFiles(
                new FilenameFilter() {
                    public boolean accept(File dir, String name) {
                        return name.endsWith(".java") || name.endsWith(".txt");
                    }
                }
        ))));

        for (File fileMatch : tempMatchingFiles) {
            matchingFiles.add(fileMatch);
        }

        File[] files = dir.listFiles();
        for (File aFile : files) {
            if (aFile.isDirectory()) {
                discoverFiles(aFile, matchingFiles);
            }
        }

        return matchingFiles;
    }

    private static String longestLine(List<String> linesOfString) {
        long count = 0;
        for (String longLine : linesOfString) {
            longLine = longLine.trim();
            if (longLine.length() == 0) //
                continue;
            if (count < longLine.length()) {
                count = longLine.length();
            }
        }
        String longestCount = String.format("%s: %d", "Longest line length", count);
        return longestCount;
    }

    private static String averageLineLength(List<String> linesOfString) {
        double totalLineLengths = 0;
        int totalLines = 0;

        for (String line : linesOfString) {
            line = line.trim();
            if (line.length() == 0)
                continue;
            totalLineLengths += line.length();
            totalLines++;
        }

        totalLineLengths /= totalLines;

        String averageLineLength = String.format("\n%s: %.2f", "Average line length", totalLineLengths);
        return averageLineLength;
    }

    private static String uniqueCaseSensitiveTokens(List<String> linesOfString) {
        List<String> tokenList = new ArrayList<>();
        long count = 0;
        String join = String.join(" ", linesOfString);
        StringTokenizer tokenizer = new StringTokenizer(join, " ");

        while (tokenizer.hasMoreTokens()) {
            String tempToken = tokenizer.nextToken();
            if (!tokenList.contains(tempToken)) {
                tokenList.add(tempToken);
                count++;
            }
        }

        String uniqueCSTokens = String.format("\n%s: %d", "Number of unique tokens (case-sensitive)", count);

        return uniqueCSTokens;
    }

    private static String uniqueCaseInsensitiveTokens(List<String> linesOfString) {
        List<String> tokenList = new ArrayList<>();
        long count = 0;
        String join = String.join(" ", linesOfString);
        StringTokenizer tokenizer = new StringTokenizer(join, " ");

        while (tokenizer.hasMoreTokens()) {
            String tempToken = tokenizer.nextToken().toLowerCase();
            if (!tokenList.contains(tempToken)) {
                tokenList.add(tempToken);
                count++;
            }
        }

        String uniqueNonCSTokens = String.format("\n%s: %d", "Number of unique tokens (case-insensitive)", count);

        return uniqueNonCSTokens;
    }

    private static String numberOfTokens(List<String> linesOfString) throws IOException {
        long wordCount = 0;

        for (String line : linesOfString) {
            line = line.trim();
            if (line.length() == 0)
                continue;
            wordCount = (line.split(" ").length) + wordCount;
        }

        String numberOfTokens = String.format("\n%s: %d", "Total number of tokens", wordCount);

        return numberOfTokens;
    }

    private static String mostFrequentTokens(List<String> linesOfString) {
        List<String> tokenList = new ArrayList<>(); //key
        List<Integer> tokenListCount = new ArrayList<>(); //value
        int largestElement = 0;
        int oldVal;
        String tempToken;
        String join = String.join(" ", linesOfString);
        StringTokenizer tokenizer = new StringTokenizer(join, " ");

        while (tokenizer.hasMoreTokens()) {
            tempToken = tokenizer.nextToken();
            if (!tokenList.contains(tempToken)) {
                tokenList.add(tempToken);
                tokenListCount.add(1);
                continue;
            }
            oldVal = tokenListCount.get(tokenList.indexOf(tempToken));
            oldVal++;
            tokenListCount.set(tokenList.indexOf(tempToken), oldVal);
        }

        // Does not account for multiple tokens with equal largest occurrences.
        for (int i = 1; i < tokenListCount.size(); i++) {
            if (tokenListCount.get(i) > tokenListCount.get(largestElement))
                largestElement = i;
        }

        String mostFrequentToken = String.format("\n%s: %s (%d matches)", "Most frequent token",
                tokenList.get(largestElement), tokenListCount.get(largestElement));

        return mostFrequentToken;
    }

    private static String tokenFrequencies(List<String> linesOfString) {
        List<Object> tokenList = new ArrayList<>();
        int oldVal;
        String tempToken;
        String join = String.join(" ", linesOfString);
        StringTokenizer tokenizer = new StringTokenizer(join, " ");

        while (tokenizer.hasMoreTokens()) {
            tempToken = tokenizer.nextToken().toLowerCase();
            if (tokenList.indexOf(tempToken) == -1) {
                tokenList.add(tempToken);
                tokenList.add(1);
                continue;
            }
            oldVal = (int) tokenList.get(tokenList.indexOf(tempToken) + 1);
            oldVal++;
            tokenList.set(tokenList.indexOf(tempToken) + 1, oldVal);
        }

        //Bubble Sort
        while (true) {
            boolean escape = true;
            for (int tokenListIndex = 1; tokenListIndex < tokenList.size() - 2; tokenListIndex += 2) {
                int rightIndex = (int) tokenList.get(tokenListIndex + 2);
                int leftIndex = (int) tokenList.get(tokenListIndex);
                if (rightIndex > leftIndex) {
                    Collections.swap(tokenList, tokenListIndex, tokenListIndex + 2);
                    Collections.swap(tokenList, tokenListIndex - 1, tokenListIndex + 1);
                    escape = false;
                }
            }
            if (escape == true)
                break;
        }

        String getTokens = String.format("\n%s", "10 most frequent tokens: ");

        for (int i = 0; i < 20 && i < tokenList.size(); i += 2) {
            getTokens += String.format("%s(%d) ", tokenList.get(i), tokenList.get(i + 1));
        }

        getTokens += String.format("\n\n%s", "10 least frequent tokens: ");

        for (int i = 0; i < 20 && i < tokenList.size(); i += 2) {
            getTokens += String.format("%s(%d) ", tokenList.get(tokenList.size() - (i + 2)),
                    tokenList.get(tokenList.size() - (i + 1)));
        }
        return getTokens;
    }
}

