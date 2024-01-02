import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        // Створення головного вікна
        JFrame frame = new JFrame("Обчислення суми ряду");

        // Створення компонентів для введення виразу, кнопки та відображення результату
        JTextField expressionField = new JTextField(20);
        JButton startButton = new JButton("Почати обчислення");
        JTextField resultField = new JTextField(20);

        // Створення панелі з компонентами
        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.add(expressionField);
        panel.add(startButton);
        panel.add(resultField);

        // Додавання панелі до головного вікна
        frame.add(panel);
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Додавання слухача подій до кнопки
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String expression = expressionField.getText();
                Thread calculationThread = new Thread(new CalculationTask(expression, resultField));
                calculationThread.start();
            }
        });
    }
}

// Клас для обчислення суми в окремому потоці
class CalculationTask implements Runnable {
    private String expression;
    private JTextField resultField;

    public CalculationTask(String expression, JTextField resultField) {
        this.expression = expression;
        this.resultField = resultField;
    }

    @Override
    public void run() {
        // Отримання суми за виразом
        double sum = calculateSum();
        // Виведення результату в поле
        resultField.setText(String.valueOf(sum));
    }

    // Обчислення суми ряду з заданою точністю
    private double calculateSum() {
        double sum = 0.0;
        double term = 1.0;
        int n = 0;
        double e = 1e-5;

        // Додаткове обчислення суми ряду з точністю e
        while (Math.abs(term) > e) {
            sum += term;
            n++;
            term = 1.0 / Math.pow(2, n);
        }

        return sum;
    }
}

