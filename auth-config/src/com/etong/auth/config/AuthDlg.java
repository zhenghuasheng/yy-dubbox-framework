package com.etong.auth.config;

import com.etong.data.auth.resource.Resource;
import com.etong.data.auth.service.AuthData;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.event.*;
import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class AuthDlg extends JFrame {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JButton buttonOpen;
    private JTree treePackage;
    private JList listMethod;
    private URLClassLoader loader;
    private String lastDir;
    private AuthData authData;

    public void setAuthData(AuthData authData) {
        this.authData = authData;
    }

    public AuthDlg() {
        setContentPane(contentPane);
//        setModal(true);
        setTitle("权限配置");
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        buttonOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onOpenDir();
            }
        });

        treePackage.setModel(null);
        treePackage.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                onPackSelChange(e);
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public void init() {
        this.pack();
        this.setSize(600, 450);
        this.setVisible(true);
    }

    private void onOK() {
        int[] sel = listMethod.getSelectedIndices();

        if (sel == null) {
            return;
        }

        for (int i = 0; i < sel.length; ++i) {
            String name = (String) listMethod.getModel().getElementAt(sel[i]);
            Resource resource = new Resource();
            resource.setRspath(name);
            resource.setStid("1001");
            resource.setAvailable(true);
            authData.putResource(resource);
        }
    }

    private void onCancel() {
        dispose();
        System.exit(0);
    }

    private void onOpenDir() {
        JFileChooser jfc = new JFileChooser(lastDir);
        jfc.setDialogTitle("请选择要配置的包目录");
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = jfc.showOpenDialog(this);

        if (JFileChooser.APPROVE_OPTION != result) {
            return;
        }

        File file = jfc.getSelectedFile();

        if (!file.isDirectory()) {
            JOptionPane.showMessageDialog(null, "你选择的目录不存在");
            return;
        }

        lastDir = file.getAbsolutePath();
        File[] packages = file.listFiles();

        if ((packages == null) || (packages.length == 0)) {
            return;
        }

        buildTree(packages);
        URL[] urls = new URL[packages.length];

        try {
            for (int i = 0; i < packages.length; ++i) {
                urls[i] = packages[i].toURI().toURL();
            }

            loader = new URLClassLoader(urls);
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        }
    }

    private void buildTree(File[] packages) {
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("包目录");
        treePackage.setModel(new DefaultTreeModel(top));

        for (File file : packages) {
            buildTreeNode(top, file);
        }

        treePackage.expandPath(new TreePath(top.getPath()));
    }

    private void buildTreeNode(DefaultMutableTreeNode parent, File pack) {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(pack.getName());
        parent.add(node);
        File[] subFiles = pack.listFiles();

        if (subFiles == null) {
            return;
        }

        for (File file : subFiles) {
            buildTreeNode(node, file);
        }
    }

    private void onPackSelChange(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                e.getNewLeadSelectionPath().getLastPathComponent();

        if (!node.isLeaf()) {
            return;
        }

        try {
            TreeNode[] path = node.getPath();
            String fullPath = "";

            for (int i = 2; i < (path.length - 1); ++i) {
                fullPath += path[i].toString() + ".";
            }

            fullPath += node.toString();
            fullPath = fullPath.replace(".class", "");
            Class aClass = loader.loadClass(fullPath);

            if (aClass != null) {
                Method[] methods = aClass.getDeclaredMethods();
                DefaultListModel<String> model = new DefaultListModel<String>();
                listMethod.setModel(model);

                for (Method method : methods) {
                    model.addElement(fullPath + "." + method.getName());
                }
            }
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
    }
}
