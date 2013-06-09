package us.scriptwith.util;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 5/13/13
 * Time: 8:34 PM
 */

public class LootTable extends JPanel {
    private final String[] headers = {"Item", "Amount", "Price", "Total"};
    private List<String[]> data = new ArrayList<>();
    private JTable table;
    private JFrame frame;
    private DefaultTableModel model = new DefaultTableModel(getDataArray(), headers);

    public LootTable() {
        super(new GridLayout(1, 0));

        frame = new JFrame("Loot Table");
        table = new JTable(getDataArray(), headers) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        final JScrollPane scrollPane = new JScrollPane(table);

        this.add(scrollPane);
        frame.setContentPane(this);

        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    public void dispose() {
        if (frame != null) {
            frame.dispose();
        }
    }

    public void addData(final String item, final int amount, final int price) {
        if (!containsItem(item)) {
            System.out.println("table does not contain item; adding " + item + " x " + amount);
            model.addRow(new Object[]{item, amount, price, (amount * price)});
            data.add(new String[]{item, amount + "", price + "", (amount * price) + ""});
        } else {
            System.out.println("table does contain item; editing " + item + " at index " + getPosition(item));
            model.setValueAt(amount, getPosition(item), 1);
            model.setValueAt((amount * price), getPosition(item), 3);
            data.set(getPosition(item), new String[]{item, amount + "", price + "", (amount * price) + ""});
        }
        table.setModel(model);
    }

    public boolean containsItem(String item) {
        for (String arr[] : data) {
            if (arr[0].equals(item)) {
                return true;
            }
        }
        return false;
    }

    private String[][] getDataArray() {
        return data.toArray(new String[data.size()][4]);
    }

    public int getCount(String item) {
        if (containsItem(item)) {
            for (String[] arr : data) {
                if (arr[0].equals(item)) {
                    return Integer.parseInt(arr[1]);
                }
            }
        }
        return 0;
    }

    public int getPosition(String item) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i)[0].equals(item)) {
                return i;
            }
        }
        return -1;
    }
}

