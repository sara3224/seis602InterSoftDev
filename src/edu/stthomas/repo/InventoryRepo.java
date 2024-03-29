package edu.stthomas.repo;

import edu.stthomas.helper.Helper;
import edu.stthomas.model.Item;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;

/**
 * Open a file and add item details (replace existing item details)
 */
public class InventoryRepo {
    private static File inventory = new File("./data/" + "inventory.tsv");

    public static void addItem(String itemId, String name, int onhands, double price, double tax, int threshold, String supplierId, int reOrderQty)  {
        if(itemExist(itemId)) {
            System.out.println("Item id exists..replaced with above details");
            removeItem(itemId);
        }
        String item = itemId + "\t" + name + "\t" +onhands + "\t" + Helper.digit2Doubles(price) +"\t" + Helper.roundUp4Digit(tax/100) + "\t"
                + threshold + "\t" + supplierId + "\t" + reOrderQty + "\t" + 0 +"\n";
        try (FileWriter fw = new FileWriter(inventory,true);
             BufferedWriter writer = new BufferedWriter(fw)) {
            writer.write(item);
        } catch (IOException e) {
            System.out.println("item addition failed: "+item);
        }
    }

    public static void updateItem(Item updatedItem)  {
        removeItem(updatedItem.getItemId());
        String item = updatedItem.getItemId()+ "\t" + updatedItem.getName() + "\t" + updatedItem.getOnhands() + "\t" + Helper.digit2Doubles(updatedItem.getPrice()) +"\t"
                + updatedItem.getTax() + "\t" + updatedItem.getThreshold() + "\t" + updatedItem.getSupplierId() + "\t"
                + updatedItem.getReorderQty() + "\t" +updatedItem.getPending() +"\n";

        try (FileWriter fw = new FileWriter(inventory,true);
             BufferedWriter writer = new BufferedWriter(fw)) {
             writer.write(item);
        } catch (IOException e) {
            System.out.println("item addition failed: "+item);
        }
    }

    private static boolean removeLine(String lineContent) throws IOException
    {
        File temp = new File(inventory.getAbsoluteFile() + ".temp");
        try (PrintWriter out = new PrintWriter(new FileWriter(temp))) {
            Files.lines(inventory.toPath())
                    .filter(line -> !line.contains(lineContent))
                    .forEach(out::println);
            out.flush();
        }
        return temp.renameTo(inventory);
    }

    static boolean itemExist(String itemId) {
        boolean exist = false;
        try (BufferedReader br = new BufferedReader(new FileReader(inventory))) {
            String st;
            String attributes[] = new String[5];
            while ((st = br.readLine()) != null) {
                attributes = st.split("\t");
                if (attributes[0].equals(itemId)) {
                    exist = true;
                }
            }
        } catch (FileNotFoundException fnf) {
            System.out.println("item deletion failed: "+itemId);
        } catch(IOException io) {
            System.out.println("item deletion failed: "+itemId);
        }
        return exist;
    }

    public static Item getItem(String itemId) {
        Item item = null;
        try (BufferedReader br = new BufferedReader(new FileReader(inventory))) {
            String st;
            String[] attributes;
            while ((st = br.readLine()) != null) {
                attributes = st.split("\t");
                if (attributes[0].equals(itemId)) {
                    item = new Item(itemId, attributes[1], Integer.valueOf(attributes[2]), Double.valueOf(attributes[3]),
                            Double.valueOf(attributes[4]), Integer.valueOf(attributes[5]),
                            attributes[6],Integer.valueOf(attributes[7]),Integer.valueOf(attributes[8]));
                }
            }
        } catch (FileNotFoundException fnf) {
            System.out.println("item deletion failed: "+itemId);
        } catch(IOException io) {
            System.out.println("item deletion failed: "+itemId);
        }
        return item;
    }

    public static void removeItem(String itemId) {
        try (BufferedReader br = new BufferedReader(new FileReader(inventory))) {
            String st;
            String[] attributes;
            while ((st = br.readLine()) != null) {
                attributes = st.split("\t");
                if (attributes[0].equals(itemId)) {
                    removeLine(st);
                }
            }
    } catch (FileNotFoundException fnf) {
            System.out.println("item deletion failed: "+itemId);
        } catch(IOException io) {
            System.out.println("item deletion failed: "+itemId);
        }
    }
}
