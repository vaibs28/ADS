import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;

/**
 * @author vaibhav 
    Entry point of the application
 */
public class hashtagcounter {

    public static void main(String[] args) throws IOException {
        boolean isOutputGiven = false;
        String inputFileName = null;
        String outputFileName = null;
        
        // if no arguments provided, exit the program
        if (args.length == 0) {
            System.out.println("Enter the input filename when running");
            System.exit(0);
        } else if (args.length == 1) {
            // only input file given
            inputFileName = args[0];
        } else if (args.length == 2) {
            isOutputGiven = true;
            inputFileName = args[0];
            outputFileName = args[1];
        }

        //map to store the heap node as value corresponding to the hashtag
        HashMap<String, Node> map = new HashMap<>();
        FibonacciHeap heap = new FibonacciHeap();
        BufferedWriter bw = null;

        try {

            File temp = new File(inputFileName);
            if(!temp.exists()) {
                System.out.println("Provided input file is not present");
                System.exit(0);
            }
            //Being used to read the input file line by line
            BufferedReader br = new BufferedReader(new InputStreamReader(hashtagcounter.class.getResourceAsStream(inputFileName)));

            //Being used for writing the results either to console or file if provided
            FileOutputStream fos;
            Writer w;
            if (isOutputGiven) {
                fos = new FileOutputStream(outputFileName);
                bw = new BufferedWriter(new OutputStreamWriter(fos));
                w = new BufferedWriter(bw);
            }

            String line;
            while (true) {
                // read the input file line by line, if encountered "stop" in input file,
                // terminate the program
                line = br.readLine();
                if (line.equalsIgnoreCase("stop"))
                    System.exit(0);

                //split the line of space and stores the strings in arr
                String[] arr = line.split(" ");
                String hashtag = null;
                String count = null;

                // create entries in the hashmap and the heap
                if (arr.length == 2) {
                    hashtag = arr[0];
                    hashtag = hashtag.substring(1); //truncate the # 
                    count = arr[1];
                    int freq = Integer.parseInt(count);
                    //first occurence of the hashtag, so insert tnto map and heap
                    if (!map.containsKey(hashtag)) {
                        Node node = heap.insert(freq, hashtag);
                        map.put(hashtag, node);
                    } else {
                        Node node = map.get(hashtag); // get the existing node
                        heap.increaseKey(node, freq); // call increase key to update the count of the hashtag
                    }
                } else if (arr.length == 1) { // query
                    String n = arr[0];
                    int queries = Integer.parseInt(n);
                    Node[] outNodeList = new Node[queries];

                    // remove and print the n most occuring hashtags from the heap
                    for (int i = 0; i < queries; i++) {
                        hashtag = heap.returnMax()
                            .getHashtag();
                        int frequency = heap.returnMax()
                            .getFrequency();
                        Node nodeToBeInserted = new Node(frequency, hashtag); // need to insert back into the heap

                        outNodeList[i] = nodeToBeInserted;

                        if (i < queries - 1) {
                            if (isOutputGiven)
                                bw.write(hashtag + ",");    //write to file if output file name is given in args
                            else
                                System.out.print(hashtag + ",");    // else print to console
                        } else {
                            if (isOutputGiven)
                                bw.write(hashtag);
                            else
                                System.out.print(hashtag);
                        }

                        map.remove(hashtag);
                        heap.removeMax();
                    }

                    if (bw != null)
                        bw.newLine();
                    else
                        System.out.println();

                    //insert the rempved node
                    for (int j = 0; j < queries; j++) {
                        Node node = heap.insert(outNodeList[j].getFrequency(), outNodeList[j].getHashtag());
                        map.put(node.getHashtag(), node);
                    }
                }

                if (bw != null)
                    bw.flush();     //to make sure the contents are flushed to file

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(bw!=null)
                bw.close();     //closes the stream
        }

    }

}
