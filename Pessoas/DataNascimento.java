package Pessoas;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public final class DataNascimento {
    private static final LocalDate DATA_ATUAL = LocalDate.of(2026, 11, 27);
    private static final LocalDate DATA_MINIMA = LocalDate.of(1900, 1, 1);
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern(
            "dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);

    private DataNascimento() {
    }

    public static LocalDate converter(String data) {
        try {
            LocalDate dataConvertida = LocalDate.parse(data, FORMATO);
            if (dataConvertida.isBefore(DATA_MINIMA)
                    || dataConvertida.isAfter(DATA_ATUAL)) {
                throw new IllegalArgumentException(
                        "o ano deve estar entre 1900 e 2026 e a data não pode ser futura");
            }
            return dataConvertida;
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "use o formato DD/MM/AAAA e indique uma data válida", e);
        }
    }

    public static int calcularIdade(String data) {
        LocalDate nascimento = converter(data);
        int idade = DATA_ATUAL.getYear() - nascimento.getYear();

        if (nascimento.plusYears(idade).isAfter(DATA_ATUAL)) {
            idade--;
        }

        return idade;
    }
}
