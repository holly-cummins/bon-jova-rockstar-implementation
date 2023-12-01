package org.example;

import io.quarkus.gizmo.ClassOutput;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BytecodeGeneratorTest {
    static class DynamicClassloader extends ClassLoader implements ClassOutput {

        byte[] classDef = null;

        @Override
        public void write(String s, byte[] bytes) {
            this.classDef = bytes;
        }

        @Override
        protected Class<?> findClass(final String name) throws ClassNotFoundException {
            if (classDef != null) {
                return defineClass(name, classDef, 0, classDef.length);
            }
            return super.findClass(name);
        }
    }


    /*
     * This is the starting example on https://codewithrockstar.com/online
     * It exercises variables, poetic number literals, and console output
     */
    @Test
    public void shouldCompileTommyIsABigBadMonster() throws IOException {
        String program = """
                Rockstar is a big bad monster
                Shout Rockstar
                """;
        String output = compileAndLaunch(program);
        String leet = "1337\n";

        assertEquals(leet, output);
    }

    private String compileAndLaunch(String program) {
        // Save the current System.out for later restoration
        PrintStream originalOut = System.out;

        DynamicClassloader loader = new DynamicClassloader();

        try {
            new BytecodeGenerator().generateBytecode(new ByteArrayInputStream(program.getBytes(StandardCharsets.UTF_8)), "whatever",
                    loader);
            Class clazz = loader.findClass("whatever");
            Method main = clazz.getMethod("main", String[].class);

            // Capture stdout since that's what the test will validate
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(outputStream);
            System.setOut(printStream);

            main.invoke(null, (Object) null);

            // Get the captured output as a string
            String capturedOutput = outputStream.toString();
            return capturedOutput;
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | IOException e) {
            throw new RuntimeException(e);
        } finally {
            // Restore the original System.out
            System.setOut(originalOut);
        }
    }

}
