import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LispMain2Test {

    @Test
    void testMainWithValidFilePath() {
        // Arrange
        String validFilePath = "ruta/al/archivo.txt"; // Ruta válida (ajusta esto según tu caso)

        // Act
        LispMain2.main(new String[]{validFilePath});
        System.out.println(validFilePath);

    }

    @Test
    void testMainWithInvalidArguments() {
        // Arrange
        String[] invalidArgs = new String[]{"arg1", "arg2"}; // Argumentos inválidos

        // Act
        LispMain2.main(invalidArgs);

        // Assert
        // Verifica si se imprimió el mensaje de uso correctamente.
    }
}
