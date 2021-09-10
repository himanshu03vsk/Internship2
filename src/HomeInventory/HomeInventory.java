package HomeInventory;
import javax.management.openmbean.InvalidOpenTypeException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.print.*;
import java.awt.event.*;
import com.toedter.calendar.*;
import java.io.*;

import java.awt.geom.Rectangle2D;
import java.beans.*;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Locale;


public class HomeInventory extends JFrame {
    JToolBar inventoryToolBar = new JToolBar("Inventory Toolbar");
    JButton newButton = new JButton(new ImageIcon("src/HomeInventory/new.png"));
    JButton deleteButton = new JButton(new ImageIcon("src/HomeInventory/delete.png"));
    JButton saveButton = new JButton(new ImageIcon("src/HomeInventory/save.png"));
    JButton previousButton = new JButton(new ImageIcon("src/HomeInventory/back.png"));
    JButton nextButton = new JButton(new ImageIcon("src/HomeInventory/forward.png"));
    JButton printButton = new JButton(new ImageIcon("src/HomeInventory/print.png"));
    JButton exitButton = new JButton(new ImageIcon("src/HomeInventory/exit.png"));


    JLabel inventoryItem = new JLabel("Inventory Item");
    JTextField inventoryItemTextField = new JTextField();

    JLabel location = new JLabel("Location");
    JComboBox locationComboBox = new JComboBox();
    JCheckBox marked = new JCheckBox("Marked?");

    JLabel serialNumber = new JLabel("Serial Number");
    JTextField serialNumberTextField = new JTextField();

    JLabel purchasePrice = new JLabel("Purchase Price");
    JTextField purchasePriceTextField = new JTextField();

    JLabel datePurchased = new JLabel("Date Purchased");
    JDateChooser dateDateChooser = new JDateChooser();

    JLabel website = new JLabel("Store/Website");
    JTextField websiteTextField = new JTextField();


    JLabel noteLabel = new JLabel("Note");
    JTextField noteTextField = new JTextField();

    JLabel photo = new JLabel("Photo");
    static JTextArea photoTextArea = new JTextArea();
    JButton photoBrowse = new JButton("...");

    JPanel searchPanel = new JPanel();
    JButton[] searchButton = new JButton[26];
    PhotoPanel photoPanel = new PhotoPanel();

    //priniting declaration

    static final int entriesPerPage = 2;
    static int lastPage;

    static final int maxEntries = 500;
    int currentEntry;
    static int numberEntries;
    static InventoryItem[] myInventory = new InventoryItem[maxEntries];

    public HomeInventory() {
        getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints gridConstraints;


        inventoryToolBar.setFloatable(false);
        inventoryToolBar.setBackground(Color.gray);
        inventoryToolBar.setOrientation(SwingConstants.VERTICAL);
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 0;
        gridConstraints.gridheight = 8;
        gridConstraints.fill = GridBagConstraints.VERTICAL;
        getContentPane().add(inventoryToolBar, gridConstraints);

        inventoryToolBar.addSeparator();

        Dimension bsize = new Dimension(70, 50);
        //newButton.setText("New");
        newButton.setBackground(Color.WHITE);
        newButton.setFocusable(false);
        sizeButton(newButton, bsize);
        newButton.setToolTipText("Add New Item");
        newButton.setHorizontalTextPosition(SwingConstants.CENTER);
        newButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        inventoryToolBar.add(newButton);
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newButtonActionPerformed(e);
            }

        });

        //deleteButton.setText("Delete");
        deleteButton.setFocusable(false);
        deleteButton.setBackground(Color.WHITE);
        sizeButton(deleteButton, bsize);
        deleteButton.setToolTipText("Delete Current Item");
        deleteButton.setHorizontalTextPosition(SwingConstants.CENTER);
        deleteButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        inventoryToolBar.add(deleteButton);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteButtonActionPerformed(e);
            }
        });

        //saveButton.setText("Save");
        saveButton.setFocusable(false);
        saveButton.setBackground(Color.WHITE);
        sizeButton(saveButton, bsize);
        saveButton.setToolTipText("Save Current Item");
        saveButton.setHorizontalTextPosition(SwingConstants.CENTER);
        saveButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        inventoryToolBar.add(saveButton);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveButtonActionPerformed(e);
            }
        });
        inventoryToolBar.addSeparator();

        //previousButton.setText("Previous");
        previousButton.setBackground(Color.WHITE);
        previousButton.setBackground(Color.WHITE);
        previousButton.setFocusable(false);
        sizeButton(previousButton, bsize);
        previousButton.setToolTipText("Display Previous Item");
        previousButton.setHorizontalTextPosition(SwingConstants.CENTER);
        previousButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        inventoryToolBar.add(previousButton);
        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousButtonActionPerformed(e);
            }
        });

        //nextButton.setText("Next");
        nextButton.setBackground(Color.WHITE);
        nextButton.setFocusable(false);
        sizeButton(nextButton, bsize);
        nextButton.setToolTipText("Display Next Item");
        nextButton.setHorizontalTextPosition(SwingConstants.CENTER);
        nextButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        inventoryToolBar.add(nextButton);
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextButtonActionPerformed(e);
            }
        });
        inventoryToolBar.addSeparator();

        //printButton.setText("Print");
        printButton.setBackground(Color.WHITE);
        printButton.setFocusable(false);
        sizeButton(printButton, bsize);
        printButton.setToolTipText("Print Inventory List");
        printButton.setHorizontalTextPosition(SwingConstants.CENTER);
        printButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        inventoryToolBar.add(printButton);
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printButtonActionPerformed(e);
            }
        });

//        exitButton.setText("Exit");
        sizeButton(exitButton, bsize);
        exitButton.setBackground(Color.WHITE);
        exitButton.setFocusable(false);
        exitButton.setToolTipText("Exit Program");
        exitButton.setHorizontalTextPosition(SwingConstants.CENTER);
        exitButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        inventoryToolBar.add(exitButton);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitButtonActionPerformed(e);
            }
        });
        inventoryToolBar.addSeparator();

        //defining insets
        Insets t_t_z_t = new Insets(10, 10, 0, 10);
        Insets t_z_z_t = new Insets(10, 0, 0, 10);
        Insets t_t_z_z = new Insets(10, 10, 0, 0);

        //inventryItem Label
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 1;
        gridConstraints.gridy = 0;
        gridConstraints.anchor = GridBagConstraints.EAST;
        gridConstraints.insets = t_t_z_t;
        getContentPane().add(inventoryItem, gridConstraints);

        //inventoryItemTextField
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 2;
        gridConstraints.gridy = 0;
        gridConstraints.gridwidth = 5;
        gridConstraints.insets = t_z_z_t;
        gridConstraints.anchor = GridBagConstraints.WEST;
        inventoryItemTextField.setPreferredSize(new Dimension(400, 25));
        inventoryItemTextField.requestFocus();
        getContentPane().add(inventoryItemTextField, gridConstraints);
        inventoryItemTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                itemTextFieldActionPerformed(e);
            }

            private void itemTextFieldActionPerformed(ActionEvent e) {
                locationComboBox.requestFocus();
            }
        });

        //locationLabel
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 1;
        gridConstraints.gridy = 1;
        gridConstraints.anchor = GridBagConstraints.EAST;
        gridConstraints.insets = t_t_z_t;
        getContentPane().add(location, gridConstraints);

        //locationTextField
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 2;
        gridConstraints.gridy = 1;
        gridConstraints.gridwidth = 3;
        gridConstraints.anchor = GridBagConstraints.WEST;
        locationComboBox.setPreferredSize(new Dimension(270, 25));
        locationComboBox.setFont(new Font("Arial", Font.PLAIN, 12));
        locationComboBox.setEditable(true);
        locationComboBox.setBackground(Color.WHITE);
        getContentPane().add(locationComboBox, gridConstraints);
        locationComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                locationComboBoxActionPerformed(e);
            }

            private void locationComboBoxActionPerformed(ActionEvent e) {

                //if loc in list exit
                if (locationComboBox.getItemCount() != 0) {
                    for (int i = 0; i < locationComboBox.getItemCount(); i++) {
                        if (locationComboBox.getSelectedItem().toString().equals(locationComboBox.getItemAt(i).toString())) {
                            serialNumberTextField.requestFocus();
                            return;
                        }
                    }
                }
                //if not found add loc to list
                locationComboBox.addItem(locationComboBox.getSelectedItem());
                serialNumberTextField.requestFocus();
            }
        });

        //checkbox
        gridConstraints = new GridBagConstraints();
        marked.setFocusable(false);
        gridConstraints.gridx = 5;
        gridConstraints.gridy = 1;
        gridConstraints.anchor = GridBagConstraints.WEST;
        gridConstraints.insets = t_t_z_z;
        getContentPane().add(marked, gridConstraints);

        //serial label
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 1;
        gridConstraints.gridy = 2;
        gridConstraints.anchor = GridBagConstraints.EAST;
        gridConstraints.insets = t_t_z_t;
        getContentPane().add(serialNumber, gridConstraints);

        //serial text field
        gridConstraints = new GridBagConstraints();
        serialNumberTextField.setPreferredSize(new Dimension(270, 25));
        gridConstraints.anchor = GridBagConstraints.WEST;
        gridConstraints.insets = t_z_z_t;
        gridConstraints.gridx = 2;
        gridConstraints.gridy = 2;
        gridConstraints.gridwidth = 3;
        getContentPane().add(serialNumberTextField, gridConstraints);
        serialNumberTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                serialNumberTextFieldActionPerformed(e);
            }

            private void serialNumberTextFieldActionPerformed(ActionEvent e) {
                purchasePriceTextField.requestFocus();
            }
        });

        //purchase price label
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 1;
        gridConstraints.gridy = 3;
        gridConstraints.insets = t_t_z_t;
        gridConstraints.anchor = GridBagConstraints.EAST;
        getContentPane().add(purchasePrice, gridConstraints);

        //purchase price text
        gridConstraints = new GridBagConstraints();
        purchasePriceTextField.setPreferredSize(new Dimension(160, 25));
        gridConstraints.anchor = GridBagConstraints.WEST;
        gridConstraints.insets = t_z_z_t;
        gridConstraints.gridx = 2;
        gridConstraints.gridy = 3;
        gridConstraints.gridwidth = 1;
        getContentPane().add(purchasePriceTextField, gridConstraints);
        purchasePriceTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                purchasePriceTextFieldActionPerformed(e);
            }

            private void purchasePriceTextFieldActionPerformed(ActionEvent e) {
                dateDateChooser.requestFocus();
            }
        });

        //date label
        gridConstraints = new GridBagConstraints();

        gridConstraints.gridx = 4;
        gridConstraints.gridy = 3;
        gridConstraints.insets = t_t_z_z;
        gridConstraints.anchor = GridBagConstraints.EAST;
        getContentPane().add(datePurchased, gridConstraints);

        //date picker control
        gridConstraints = new GridBagConstraints();
        dateDateChooser.setPreferredSize(new Dimension(120, 25));
        gridConstraints.insets = t_z_z_t;
        gridConstraints.gridx = 5;
        gridConstraints.gridy = 3;
        gridConstraints.gridwidth = 2;
        gridConstraints.anchor = GridBagConstraints.WEST;
        getContentPane().add(dateDateChooser, gridConstraints);
        dateDateChooser.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                dateDateChooserPropertyChanged(evt);
            }

            private void dateDateChooserPropertyChanged(PropertyChangeEvent evt) {
                websiteTextField.requestFocus();
            }
        });


        //store label
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 1;
        gridConstraints.gridy = 4;
        gridConstraints.anchor = GridBagConstraints.EAST;
        gridConstraints.insets = t_t_z_t;
        getContentPane().add(website, gridConstraints);

        //website/store text field
        websiteTextField.setPreferredSize(new Dimension(400, 25));
        gridConstraints = new GridBagConstraints();
        gridConstraints.insets = t_z_z_t;
        gridConstraints.anchor = GridBagConstraints.WEST;
        gridConstraints.gridx = 2;
        gridConstraints.gridy = 4;
        gridConstraints.gridwidth = 5;
        getContentPane().add(websiteTextField, gridConstraints);
        websiteTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                websiteTextFieldActionPerformed(e);
            }

            private void websiteTextFieldActionPerformed(ActionEvent e) {
                noteTextField.requestFocus();
            }
        });

        //note label
        gridConstraints = new GridBagConstraints();
        gridConstraints.insets = t_t_z_t;
        gridConstraints.anchor = GridBagConstraints.EAST;
        gridConstraints.gridx = 1;
        gridConstraints.gridy = 5;
        getContentPane().add(noteLabel, gridConstraints);

        //note text field
        gridConstraints = new GridBagConstraints();
        noteTextField.setPreferredSize(new Dimension(400, 25));
        gridConstraints.insets = t_z_z_t;
        gridConstraints.anchor = GridBagConstraints.WEST;
        gridConstraints.gridx = 2;
        gridConstraints.gridy = 5;
        gridConstraints.gridwidth = 5;
        getContentPane().add(noteTextField, gridConstraints);
        noteTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                noteTextFieldActionPerformed(e);
            }

            private void noteTextFieldActionPerformed(ActionEvent e) {
                photoBrowse.requestFocus();
            }
        });

        //photo label
        gridConstraints = new GridBagConstraints();
        gridConstraints.insets = t_t_z_t;
        gridConstraints.anchor = GridBagConstraints.EAST;
        gridConstraints.gridx = 1;
        gridConstraints.gridy = 6;
        getContentPane().add(photo, gridConstraints);

        //photo text field
        photoTextArea.setSize(new Dimension(350, 35));
        photoTextArea.setFont(new Font("Arial", Font.PLAIN, 12));
        photoTextArea.setEditable(false);
        photoTextArea.setLineWrap(true);
        photoTextArea.setFocusable(false);
        photoTextArea.setWrapStyleWord(true);
        photoTextArea.setBackground(new Color(255, 255, 192));
        photoTextArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        gridConstraints.insets = t_z_z_t;
        gridConstraints.anchor = GridBagConstraints.WEST;
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 2;
        gridConstraints.gridy = 6;
        gridConstraints.gridwidth = 4;
        getContentPane().add(photoTextArea, gridConstraints);

        //photo button
        gridConstraints = new GridBagConstraints();
        gridConstraints.anchor = GridBagConstraints.WEST;
        gridConstraints.insets = t_t_z_t;
        gridConstraints.gridx = 6;
        gridConstraints.gridy = 6;
        getContentPane().add(photoBrowse, gridConstraints);
        photoBrowse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                photoButtonActionPerformed(e);
            }
        });

        //search panel
        searchPanel.setPreferredSize(new Dimension(240, 160));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Item Search"));
        searchPanel.setLayout(new GridBagLayout());
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 1;
        gridConstraints.gridy = 7;
        gridConstraints.gridwidth = 3;
        gridConstraints.insets = t_z_z_t;
        gridConstraints.anchor = GridBagConstraints.CENTER;
        getContentPane().add(searchPanel, gridConstraints);

        //search button placements
        int x = 0;
        int y = 0;
        //create and position 26 buttons
        for (int i = 0; i < 26; i++) {
            searchButton[i] = new JButton();
            searchButton[i].setFocusable(false);
            //set text property
            searchButton[i].setText(String.valueOf((char) (65 + i)));
            searchButton[i].setFont(new Font("Arial", Font.BOLD, 12));
            searchButton[i].setBackground(Color.YELLOW);
            sizeButton(searchButton[i], new Dimension(37, 27));
            searchButton[i].setMargin(new Insets(-10, -10, -10, -10));
            gridConstraints = new GridBagConstraints();
            gridConstraints.gridx = x;
            gridConstraints.gridy = y;
            searchPanel.add(searchButton[i], gridConstraints);
            searchButton[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    searchButtonActionPerformed(e);
                }
            });
            x++;
            if (x % 6 == 0) {
                x = 0;
                y++;
            }
        }

        //picture panel
        photoPanel.setPreferredSize(new Dimension(240, 160));

        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 4;
        gridConstraints.gridy = 7;
        gridConstraints.gridwidth = 3;
        gridConstraints.insets = new Insets(10, 0, 10, 10);
        gridConstraints.anchor = GridBagConstraints.CENTER;
        getContentPane().add(photoPanel, gridConstraints);


        setTitle("Home Inventory Manager");
//        setResizable(false);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitForm(e);
            }
        });
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);


        pack();
        Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((int) (0.5 * (screen_size.width - getWidth())), (int) (0.5 * (screen_size.height - getHeight())), getWidth(), getHeight());
        setVisible(true);
        setResizable(false);


        //reading in inventory.txt file


        int n;
        try {
            BufferedReader input = new BufferedReader(new FileReader("C:\\Users\\Luffy\\Desktop\\Internship\\HomeInverntory\\src\\HomeInventory\\inventory.txt"));
            numberEntries = Integer.parseInt(input.readLine());
            if (numberEntries != 0) {
                for (int i = 0; i < numberEntries; i++) {
                    myInventory[i] = new InventoryItem();
                    myInventory[i].description = input.readLine();
                    myInventory[i].location = input.readLine();
                    myInventory[i].serialNumber = input.readLine();
                    myInventory[i].markedIndicator = Boolean.valueOf(input.readLine());
                    myInventory[i].purchasePrice = input.readLine();
                    myInventory[i].purchaseDate = input.readLine();
                    myInventory[i].purchaseLocation = input.readLine();
                    myInventory[i].note = input.readLine();
                    myInventory[i].photoFile = input.readLine();
                }
            }
            //read combo box elements
            n = Integer.parseInt(input.readLine());
            if (n != 0) {
                for (int i = 0; i < n; i++) {
                    locationComboBox.addItem(input.readLine());
                }
            }
            input.close();
            currentEntry = 1;
            showEntry(currentEntry);
        } catch (Exception ex) {
            numberEntries = 0;
            currentEntry = 0;
//            System.out.println("Exception");
        }

        if (numberEntries == 0) {
            newButton.setEnabled(true);
            deleteButton.setEnabled(false);
            nextButton.setEnabled(false);
            previousButton.setEnabled(false);
            printButton.setEnabled(false);
        }

    }

    private void searchButtonActionPerformed(ActionEvent e) {
        int i;
        if (numberEntries == 0 ){return;}
        //search for item letter
        String clickedLetter = e.getActionCommand();
        i = 0;
        do{
            if(myInventory[i].description.substring(0,1).equals(clickedLetter)){
                currentEntry = i+1;
                showEntry(currentEntry);
                return;
            }
            i++;
        }while (i < numberEntries);
        JOptionPane.showConfirmDialog(null, "NO "+ clickedLetter + " inventory items", "None Found", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
    }

    private void photoButtonActionPerformed(ActionEvent e) {
        JFileChooser openChooser = new JFileChooser();
        openChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        openChooser.setDialogTitle("Open Photo file");
        openChooser.addChoosableFileFilter(new FileNameExtensionFilter("Photo Files", "jpg"));
        if (openChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            showPhoto(openChooser.getSelectedFile().toString());
        }
    }

    private void exitButtonActionPerformed(ActionEvent e) {
        try {
            PrintWriter outputFile = new PrintWriter(new BufferedWriter(new FileWriter("src/HomeInventory/inventory.txt")));
            outputFile.println(numberEntries);
            if (numberEntries != 0) {
                for (int i = 0; i < numberEntries; i++) {
                    outputFile.println(myInventory[i].description);
                    outputFile.println(myInventory[i].location);
                    outputFile.println(myInventory[i].serialNumber);
                    outputFile.println(myInventory[i].markedIndicator);
                    outputFile.println(myInventory[i].purchasePrice);
                    outputFile.println(myInventory[i].purchaseDate);
                    outputFile.println(myInventory[i].purchaseLocation);
                    outputFile.println(myInventory[i].note);
                    outputFile.println(myInventory[i].photoFile);
                }
            }
            //write combo box entries
            int itemCount = locationComboBox.getItemCount();
            outputFile.println(itemCount);
            if (itemCount != 0) {
                for (int i = 0; i < itemCount; i++) {
                    outputFile.println(locationComboBox.getItemAt(i));
                }
            }
            outputFile.close();
        } catch (Exception ex) {
        }
        exitForm(null);
    }

    private void printButtonActionPerformed(ActionEvent e) {
            lastPage = (int) (1 + (numberEntries - 1) / entriesPerPage);
            PrinterJob inventoryPrinterJob = PrinterJob.getPrinterJob();
            inventoryPrinterJob.setPrintable(new InventoryDocument());
            if (inventoryPrinterJob.printDialog())
            {
                try
                {
                    inventoryPrinterJob.print();
                }

                catch (PrinterException ex)
                {
                    JOptionPane.showConfirmDialog(null, ex.getMessage(), "Print Error",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                }
            }
    }

    private void nextButtonActionPerformed(ActionEvent e) {
        checkSave();
        currentEntry++;
        showEntry(currentEntry);
    }

    private void previousButtonActionPerformed(ActionEvent e) {
        checkSave();
        currentEntry--;
        showEntry(currentEntry);
    }

    private void saveButtonActionPerformed(ActionEvent e) {

        //check for description
        inventoryItemTextField.setText(inventoryItemTextField.getText().trim());
        if(inventoryItemTextField.getText().equals("")){
                JOptionPane.showConfirmDialog(null, "Must have item description", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                inventoryItemTextField.requestFocus();
                return;
        }
        if (newButton.isEnabled()){
            //delete the current entry and then resave
            deleteEntry(currentEntry);
        }
        //if not empty capitalize first letter
        String s = inventoryItemTextField.getText();
        inventoryItemTextField.setText(s.substring(0,1).toUpperCase(Locale.ROOT) + s.substring(1));
        numberEntries++;
        //determine new current entry location based on description
        currentEntry = 1;
        if (numberEntries != 1){
            do{
                if(inventoryItemTextField.getText().compareTo(myInventory[currentEntry-1].description) < 0)
                    break;
                currentEntry++;
            } while (currentEntry < numberEntries);
        }
        //move all entries below new value down one position unless at end
        if (currentEntry != numberEntries){
            for (int i = numberEntries; i >= currentEntry + 1; i--)
            {
                myInventory[i - 1] = myInventory[i - 2];
                myInventory[i - 2] = new InventoryItem();
            }
        }
        myInventory[currentEntry - 1] = new InventoryItem();
        myInventory[currentEntry - 1].description = inventoryItemTextField.getText();
        myInventory[currentEntry - 1].location = locationComboBox.getSelectedItem().toString();
        myInventory[currentEntry - 1].markedIndicator = marked.isSelected();
        myInventory[currentEntry - 1].serialNumber = serialNumberTextField.getText();
        myInventory[currentEntry - 1].purchasePrice = purchasePriceTextField.getText();
        myInventory[currentEntry - 1].purchaseDate = dateToString(dateDateChooser.getDate());
        myInventory[currentEntry - 1].purchaseLocation = websiteTextField.getText();
        myInventory[currentEntry - 1].photoFile = photoTextArea.getText();
        myInventory[currentEntry - 1].note = noteTextField.getText();
        showEntry(currentEntry);
        if(numberEntries < maxEntries){
            newButton.setEnabled(true);
        }else{
            newButton.setEnabled(false);
        }
        deleteButton.setEnabled(true);
        printButton.setEnabled(true);

    }

    private void deleteButtonActionPerformed(ActionEvent e) {
        if (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this item?","Delete Inventory item", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION){
            return;
        }
        deleteEntry(currentEntry);
        if (numberEntries == 0){
            currentEntry = 0;
            blankValues();
        }
        else{
            currentEntry--;
            if(currentEntry == 0){
                currentEntry = 1;
            }
            showEntry(currentEntry);

        }
    }

    private void deleteEntry(int entry) {
        if (entry != numberEntries){
            //move everything up one level
            for (int i=entry ; i< numberEntries; i++){
                myInventory[i-1] = new InventoryItem();
                myInventory[i-1] = myInventory[i];
            }
        }
        numberEntries--;
    }

    private void newButtonActionPerformed(ActionEvent e) {
        checkSave();
        blankValues();
    }



//    private void checkSave() {
//        boolean edited = false;
//        //System.out.println(myInventory[0] == null);
//        if (myInventory[0] == null){return;}
//        if(!myInventory[currentEntry-1].description.equals(inventoryItemTextField.getText())){edited = true;}
//        else if(!myInventory[currentEntry-1].location.equals(locationComboBox.getSelectedItem().toString())){edited = true;}
//        else if(myInventory[currentEntry-1].markedIndicator != marked.isSelected()){edited = true;}
//        else if(!myInventory[currentEntry-1].serialNumber.equals(serialNumberTextField.getText())){edited = true;}
//        else if(!myInventory[currentEntry-1].purchasePrice.equals(purchasePriceTextField.getText())){edited = true;}
//        else if(!myInventory[currentEntry-1].purchaseDate.equals(dateToString(dateDateChooser.getDate()))){edited = true;}
//        else if(!myInventory[currentEntry-1].purchaseLocation.equals(websiteTextField.getText())){edited = true;}
//        else if(myInventory[currentEntry-1].note.equals(noteTextField.getText())){edited = true;}
//        else if(myInventory[currentEntry-1].photoFile.equals(photoTextArea.getText())){edited = true;}
//
//        if(edited){
//            if (JOptionPane.showConfirmDialog(null, "You have edited this item, Do you want to save the changes made?", "Save Item", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
//                saveButton.doClick();
//            }
//        }
//    }
private void checkSave() {
    boolean edited = false;
    if (myInventory[0] == null){return;}
    if (!myInventory[currentEntry - 1].description.equals(inventoryItemTextField.getText()))
        edited = true;
else if (!myInventory[currentEntry -
            1].location.equals(locationComboBox.getSelectedItem().toString()))
        edited = true;
    else if (myInventory[currentEntry - 1].markedIndicator != marked.isSelected())
        edited = true;
    else if (!myInventory[currentEntry - 1].serialNumber.equals(serialNumberTextField.getText()))
        edited = true;
    else if (!myInventory[currentEntry - 1].purchasePrice.equals(purchasePriceTextField.getText()))
        edited = true;
    else if (!myInventory[currentEntry -
            1].purchaseDate.equals(dateToString(dateDateChooser.getDate())))
        edited = true;
    else if (!myInventory[currentEntry -
            1].purchaseLocation.equals(websiteTextField.getText()))
        edited = true;
    else if (!myInventory[currentEntry - 1].note.equals(noteTextField.getText()))
        edited = true;
    else if (!myInventory[currentEntry - 1].photoFile.equals(photoTextArea.getText()))
        edited = true;
    if (edited) {
        if (JOptionPane.showConfirmDialog(null, "You have edited this item. Do you want to save the changes ? ", " Save Item", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
            saveButton.doClick();
    }
}

    private void sizeButton(JButton b, Dimension d) {
        b.setPreferredSize(d);
        b.setMinimumSize(d);
        b.setMaximumSize(d);
    }

    private void exitForm(WindowEvent evt) {
        if (JOptionPane.showConfirmDialog(null, "Any unsaved changes will be lost.\nAre you sure you want to exit?", "Exit Program", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION)
            return;
        System.exit(0);
    }

    private void showEntry(int j) {
        //display entry
        inventoryItemTextField.setText(myInventory[j - 1].description);
        locationComboBox.setSelectedItem(myInventory[j - 1].location);
        marked.setSelected(myInventory[j - 1].markedIndicator);
        serialNumberTextField.setText(myInventory[j - 1].serialNumber);
        purchasePriceTextField.setText(myInventory[j - 1].purchasePrice);
        dateDateChooser.setDate(stringToDate(myInventory[j - 1].purchaseDate));
        websiteTextField.setText(myInventory[j - 1].purchaseLocation);
        noteTextField.setText(myInventory[j - 1].note);
        showPhoto(myInventory[j - 1].photoFile);
        nextButton.setEnabled(true);
        previousButton.setEnabled(true);
        if (j == 1) {
            previousButton.setEnabled(false);
        }
        if (j == numberEntries) {
            nextButton.setEnabled(false);

        }
        inventoryItemTextField.requestFocus();

    }

    private void showPhoto(String photoFile) {
        if (!photoFile.equals("")) {
            try {
                photoTextArea.setText(photoFile);
            } catch (Exception ex) {
                photoTextArea.setText("");
            }
        } else {
            photoTextArea.setText("");
        }
        photoPanel.repaint();
    }

    private Date stringToDate(String date) {
        int m = Integer.valueOf(date.substring(0, 2)) - 1;
        int d = Integer.valueOf(date.substring(3, 5));
        int y = Integer.valueOf(date.substring(6)) - 1900;
        return (new Date(y, m, d));
    }

    private String dateToString(Date date) {
        String yString = String.valueOf(date.getYear() + 1900);
        int m = date.getMonth() + 1;
        String mString = new DecimalFormat("00").format(m);
        int d = date.getDate();
        String dString = new DecimalFormat("00").format(d);
        return (mString + "/" + dString + "/" + yString);

    }


    public static void main(String[] args) {
        new HomeInventory();


    }

    private void blankValues() {
        //blank input screen
        newButton.setEnabled(false);
        deleteButton.setEnabled(false);
        saveButton.setEnabled(true);
        locationComboBox.setSelectedItem("");
        previousButton.setEnabled(false);
        nextButton.setEnabled(false);
        printButton.setEnabled(false);
        inventoryItemTextField.setText("");
        websiteTextField.setText("");
        serialNumberTextField.setText("");
        purchasePriceTextField.setText("");
        noteTextField.setText("");
        photoTextArea.setText("");
        dateDateChooser.setDate(new Date());
        marked.setSelected(false);
        photoPanel.repaint();
        inventoryItemTextField.requestFocus();
    }


}


