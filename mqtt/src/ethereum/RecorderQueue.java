package ethereum;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * file d'attente pour la publication de résultat sur la blockchain
 */
public class RecorderQueue extends Thread {

    /**
     * file fifo de request
     */
    private final Queue<Entry> queue;

    /**
     * recorder pour l'écriture sur la blockchain
     */
    private final Recorder recorder;

    /**
     * initialise la RecorderQueue
     */
    public RecorderQueue() {
	this.queue = new ConcurrentLinkedQueue<>();
	this.recorder = new Recorder();
    }

    @Override
    public void run() {
	while (!this.isInterrupted()) {
	    if (this.queue.size() > 0) {
		this.recorder.createRecord(this.queue.poll());
	    } else {
		this.synchWait();
	    }
	}
    }

    /**
     * attends 2s avant de reverifier si l'on peut publier des résultats
     */
    synchronized private void synchWait() {
	try {
	    this.wait(2000);
	} catch (InterruptedException e) {
	    this.interrupt();
	}
    }

    /**
     * ajoute un résultat à la queue et notify le thread pour qu'il l'ajoute
     * 
     * @param e
     */
    synchronized public void addToQueue(Entry e) {
	this.queue.add(e);
	this.notifyAll();
    }
}
