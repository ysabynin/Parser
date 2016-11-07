package com.ysabynin;

import java.util.ArrayList;
import java.util.List;

public class NodeElement {
    public String name;
    public ArrayList<NodeElement> childs;

    public NodeElement(String n) {
        childs = new ArrayList<>();
        name = n;
    }

    public void addChildren(NodeElement ch) {
        childs.add(ch);
    }

    public void print(int level) {
        String offset = "";
        for (int i = 0; i < level; i++) {
            offset += "\t";
        }

        System.out.println(offset + name);

        for (int i = 0; i < childs.size(); i++) {
            childs.get(i).print(level + 1);
        }
    }
}
