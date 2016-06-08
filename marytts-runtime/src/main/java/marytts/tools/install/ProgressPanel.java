/**
 * Copyright 2009 DFKI GmbH.
 * All Rights Reserved.  Use is subject to license terms.
 *
 * This file is part of MARY TTS.
 *
 * MARY TTS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

/*
 * ProgressPanel.java
 *
 * Created on 21. September 2009, 15:03
 */

package marytts.tools.install;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;

import marytts.tools.install.ComponentDescription.Status;

/**
 *
 * @author marc
 */
public class ProgressPanel extends javax.swing.JPanel implements Runnable, Observer {
	private List<ComponentDescription> allComponents;
	private ComponentDescription currentComponent = null;
	private boolean install;
	private boolean exitRequested = false;

	/**
	 * Creates new form ProgressPanel
	 * 
	 * @param componentsToProcess
	 *            componentsToProcess
	 * @param install
	 *            install
	 */
	public ProgressPanel(List<ComponentDescription> componentsToProcess, boolean install) {
		this.allComponents = componentsToProcess;
		this.install = install;
		initComponents();
	}

	public synchronized void requestExit() {
		this.exitRequested = true;
		if (currentComponent != null) {
			currentComponent.cancel();
		}
	}

	private synchronized boolean isExitRequested() {
		return exitRequested;
	}

	private void setCurrentComponent(ComponentDescription desc) {
		if (currentComponent != null) {
			currentComponent.deleteObserver(this);
		}
		currentComponent = desc;
		if (currentComponent != null) {
			currentComponent.addObserver(this);
		}
		verifyCurrentComponentDisplay();
	}

	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of
	 * this method is always regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
	private void initComponents() {
		pCurrent = new javax.swing.JPanel();
		lName = new javax.swing.JLabel();
		lNameValue = new javax.swing.JLabel();
		lStatus = new javax.swing.JLabel();
		lStatusValue = new javax.swing.JLabel();
		pbCurrent = new javax.swing.JProgressBar();
		pOverall = new javax.swing.JPanel();
		pbOverall = new javax.swing.JProgressBar();

		pCurrent.setBorder(javax.swing.BorderFactory.createTitledBorder("Current component"));
		lName.setFont(new java.awt.Font("Lucida Grande", 1, 13));
		lName.setText("Name:");

		lNameValue.setText(currentComponent != null ? currentComponent.getName() : "");

		lStatus.setFont(new java.awt.Font("Lucida Grande", 1, 13));
		lStatus.setText("Status:");

		lStatusValue.setText(currentComponent != null ? currentComponent.getStatus().toString() : "");

		pbCurrent.setStringPainted(true);

		org.jdesktop.layout.GroupLayout pCurrentLayout = new org.jdesktop.layout.GroupLayout(pCurrent);
		pCurrent.setLayout(pCurrentLayout);
		pCurrentLayout.setHorizontalGroup(pCurrentLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
				pCurrentLayout
						.createSequentialGroup()
						.add(pCurrentLayout
								.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
								.add(pCurrentLayout.createSequentialGroup().add(lName)
										.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED).add(lNameValue))
								.add(pCurrentLayout
										.createSequentialGroup()
										.add(lStatus)
										.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
										.add(lStatusValue)
										.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 50, Short.MAX_VALUE)
										.add(pbCurrent, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 184,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))).addContainerGap()));
		pCurrentLayout.setVerticalGroup(pCurrentLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
				pCurrentLayout
						.createSequentialGroup()
						.add(pCurrentLayout
								.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
								.add(pCurrentLayout
										.createSequentialGroup()
										.add(pCurrentLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
												.add(lName).add(lNameValue))
										.add(16, 16, 16)
										.add(pCurrentLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
												.add(lStatus).add(lStatusValue)))
								.add(pCurrentLayout
										.createSequentialGroup()
										.add(27, 27, 27)
										.add(pbCurrent, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		pOverall.setBorder(javax.swing.BorderFactory.createTitledBorder("Overall progress"));
		pbOverall.setString("");
		pbOverall.setStringPainted(true);

		org.jdesktop.layout.GroupLayout pOverallLayout = new org.jdesktop.layout.GroupLayout(pOverall);
		pOverall.setLayout(pOverallLayout);
		pOverallLayout.setHorizontalGroup(pOverallLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
				org.jdesktop.layout.GroupLayout.TRAILING,
				pOverallLayout.createSequentialGroup().addContainerGap()
						.add(pbOverall, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE).addContainerGap()));
		pOverallLayout.setVerticalGroup(pOverallLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
				org.jdesktop.layout.GroupLayout.TRAILING,
				pOverallLayout
						.createSequentialGroup()
						.addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.add(pbOverall, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));

		org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(pCurrent, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)
				.add(pOverall, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
				layout.createSequentialGroup()
						.add(pCurrent, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
						.add(pOverall, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)));
	}// </editor-fold>//GEN-END:initComponents

	public void run() {
		boolean error = false;
		ComponentDescription problematic = null;
		int i = 0;
		int max = allComponents.size();
		pbOverall.setMaximum(max);
		String action = install ? "install" : "uninstall";
		for (ComponentDescription comp : allComponents) {
			if (isExitRequested())
				return;
			System.out.println("Now " + action + "ing " + comp.getName() + "...");
			pbOverall.setValue(i);
			pbOverall.setString(i + " / " + max);
			setCurrentComponent(comp);
			if (install) {
				ComponentDescription orig = null;
				if (comp.getStatus() == Status.INSTALLED) { // Installing an installed component really means replacing it with
															// its updated version
					assert comp.isUpdateAvailable();
					// 1. uninstall current version; 2. install replacement
					comp.uninstall();
					if (comp.getStatus() == Status.ERROR) {
						error = true;
					} else {
						if (comp.isUpdateAvailable()) {
							comp.replaceWithUpdate();
						}
					}
					// And from here on, treat comp like any other component to install
				}
				if (!error && comp.getStatus() == Status.AVAILABLE || comp.getStatus() == Status.CANCELLED) {
					comp.download(true);
					if (comp.getStatus() == Status.ERROR) {
						error = true;
					}
				}
				if (!error && comp.getStatus() == Status.DOWNLOADED) {
					try {
						comp.install(true);
					} catch (Exception e) {
						e.printStackTrace();
						error = true;
					}
					if (comp.getStatus() == Status.ERROR) {
						error = true;
					}
				}
			} else { // uninstall
				if (comp.getStatus() == Status.INSTALLED) {
					comp.uninstall();
					if (comp.getStatus() == Status.ERROR) {
						error = true;
					} else {
						if (comp.isUpdateAvailable()) {
							comp.replaceWithUpdate();
						}
					}
				}
			}
			if (error) {
				problematic = comp;
				System.err.println("Could not " + action + " " + comp.getName());
				break;
			}
			comp.setSelected(false);
			i++;
		}
		if (error) {
			assert problematic != null;
			JOptionPane.showMessageDialog(this, "Could not " + action + " " + problematic.getName());
		} else {
			pbOverall.setValue(max);
			pbOverall.setString(max + " / " + max);
			// JOptionPane.showMessageDialog(this, max + " components "+action+"ed successfully.");
		}
		this.setCurrentComponent(null);
		this.getTopLevelAncestor().setVisible(false);
	}

	public void update(Observable o, Object arg) {
		if (o != currentComponent) {
			throw new IllegalStateException("We are observing " + o + " but the currentComponent is " + currentComponent);
		}
		verifyCurrentComponentDisplay();
	}

	private void verifyCurrentComponentDisplay() {
		if (currentComponent == null)
			return;
		lNameValue.setText(currentComponent.getName());
		lStatusValue.setText(currentComponent.getStatus().toString());
		int progress = currentComponent.getProgress();
		if (progress < 0) {
			pbCurrent.setIndeterminate(true);
		} else {
			pbCurrent.setIndeterminate(false);
			pbCurrent.setValue(progress);
		}

	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JLabel lName;
	private javax.swing.JLabel lNameValue;
	private javax.swing.JLabel lStatus;
	private javax.swing.JLabel lStatusValue;
	private javax.swing.JPanel pCurrent;
	private javax.swing.JPanel pOverall;
	private javax.swing.JProgressBar pbCurrent;
	private javax.swing.JProgressBar pbOverall;
	// End of variables declaration//GEN-END:variables

}
