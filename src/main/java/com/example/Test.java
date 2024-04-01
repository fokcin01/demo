package com.example;

import javax.swing.*;
import java.awt.*;

public class Test implements Runnable {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Test());
    }

    @Override
    public void run() {
        //создаем общий фрейм
        JFrame frame = new JFrame("test");
        frame.setSize(500, 300);
        //говорим, что этот фрейм будет располагаться на BorderLayout
        frame.setLayout(new BorderLayout());
        //создаем одну панель с текстфилдами и лейблами для расположения
        //этой панели в центральной части BorderLayout
        JPanel panelWithLabelsAndFields = new JPanel();
        //и вторую панель для кнопок, располагаться будет в южной части BorderLayout
        JPanel panelWithButtons = new JPanel();
        //создадим лейблы, филды и батоны
        JLabel idLabel = new JLabel("id");
        JTextField idField = new JTextField("1");
        JLabel nameLabel = new JLabel("name");
        JTextField nameField = new JTextField("амфетамин");
        JLabel priceLabel = new JLabel("price");
        JTextField priceField = new JTextField("999");
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        //создаем layout, в котором будут лежать лейблы с филдами
        GridBagLayout gridBagLayout = new GridBagLayout();
        //говорим, что панель будет лежать в этом лэйауте
        panelWithLabelsAndFields.setLayout(gridBagLayout);
        //добавляем на панель наши лейблы и филды

        //насчет GridBagConstraints
        //вообще - это сетка, типа таблицы. Координата х = номер колонки, y - номер строчки
        //gridx - колонка в сетке
        //gridy - строка в сетке
        //gridwidth - сколько ячеек в СТРОКЕ займет элемент
        //gridheight - сколько ячеек в столбце займет элемент
        //weightx - забей хуй
        //weighty - забей хуй
        //GridBagConstraints.EAST к какому краю ячейки сетки "прилипнет" элемент, который в ней лежит
        //GridBagConstraints.HORIZONTAL - растянуть элемент внутри ячейки (например, по горизонтали). Если поставишь BOTH, то по гориз. и вертикали
        //new Insets - отступы от краев ячейки. То есть элемент, который лежит в ячейке сетки, будет отступать от краев этой ячейки установленное кол-во пикселей
        //ipadx - забей хуй
        //ipady - забей хуй
        panelWithLabelsAndFields.add(idLabel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        panelWithLabelsAndFields.add(idField, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        panelWithLabelsAndFields.add(nameLabel, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        panelWithLabelsAndFields.add(nameField, new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        panelWithLabelsAndFields.add(priceLabel, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        panelWithLabelsAndFields.add(priceField, new GridBagConstraints(1, 2, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        //добавляем на панель кнопок наши батоны
        panelWithButtons.add(saveButton);
        panelWithButtons.add(cancelButton);


//        panelWithLabelsAndFields.setBackground(Color.GRAY);
        panelWithButtons.setBackground(Color.PINK);

        //говорим - добавь на основной фрейм (в котором лэйаут BorderLayout) в центр панельку с лейблами
        frame.add(panelWithLabelsAndFields, BorderLayout.CENTER);
        //а на южную часть добавь панель с кнопками
        frame.add(panelWithButtons, BorderLayout.SOUTH);

        //отобрази основной фрейм
//        frame.pack();
        frame.setVisible(true);
    }
}
