package com.ngosdi.lawyer.app.views.legalcase;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.ngosdi.lawyer.app.BundleUtil;
import com.ngosdi.lawyer.app.views.client.ClientDataListModel;
import com.ngosdi.lawyer.app.views.common.TitledSeparator;
import com.ngosdi.lawyer.app.views.common.customizer.AbstractCustomizer;
import com.ngosdi.lawyer.app.views.common.customizer.IValidationSupport;
import com.ngosdi.lawyer.app.views.common.customizer.ObjectSeekComposite;
import com.ngosdi.lawyer.app.views.common.customizer.SearchContext;
import com.ngosdi.lawyer.app.views.common.datalist.DataListModel;
import com.ngosdi.lawyer.app.views.common.proxy.ProxyFactory;
import com.ngosdi.lawyer.app.views.court.CourtDataListModel;
import com.ngosdi.lawyer.beans.Case;
import com.ngosdi.lawyer.beans.Client;
import com.ngosdi.lawyer.beans.ClientType;
import com.ngosdi.lawyer.beans.Court;
import com.ngosdi.lawyer.beans.CourtType;
import com.ngosdi.lawyer.beans.Nature;
import com.ngosdi.lawyer.beans.Section;
import com.ngosdi.lawyer.services.IServiceDao;

public class CaseCustomizer extends AbstractCustomizer<Case> {
	final CaseCustomizerModel customizerModel;

	public CaseCustomizer(final Case legalCase) {
		super(legalCase, "Nouvelle affaire", "Cette fenêtre permet d'ajouter une nouvelle affaire. les champs marqué avec (*) sont obligatoires.");
		customizerModel = ProxyFactory.createProxy(new CaseCustomizerModel(legalCase));
	}

	@Override
	public Composite createArea(final Composite parent, final int style) {
		parent.setLayout(new GridLayout());
		final Composite composite = new Composite(parent, SWT.NONE);
		final GridLayout gl_composite = new GridLayout(6, false);
		composite.setLayout(gl_composite);
		final Label caseIdLabel = new Label(composite, SWT.NONE);
		caseIdLabel.setText("Numéro Affaire:");

		final Text caseIdText = new Text(composite, SWT.BORDER);
		caseIdText.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false));
		bindingSupport.bindText(customizerModel, "caseId", caseIdText, IValidationSupport.NOT_EMPTY_VALIDATOR);

		new TitledSeparator(composite, "Parties").setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 6, 1));

		final Label clientLabel = new Label(composite, SWT.NONE);
		clientLabel.setText("Pour:");

		final LabelProvider labelProvider = new LabelProvider() {
			@Override
			public String getText(final Object element) {
				final Client client = (Client) element;
				return client.getFirstName() + ", " + client.getLastName();
			}
		};
		final DataListModel clientDataListModel = new ClientDataListModel(parent, ClientType.CLIENT);
		final String title = "Recherche client";
		final String description = "Cette fenetre permet de rechercher un client";
		final SearchContext searchContext = new SearchContext(clientDataListModel, labelProvider, title, description);

		final ObjectSeekComposite clientSeekComposite = new ObjectSeekComposite(composite, searchContext);
		clientSeekComposite.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 2, 1));
		bindingSupport.bindObjectSeekComposite(customizerModel, "client", clientSeekComposite, IValidationSupport.NOT_EMPTY_VALIDATOR);

		final Label oppositeLabel = new Label(composite, SWT.NONE);
		oppositeLabel.setText("Contre:");

		final DataListModel advDataListModel = new ClientDataListModel(parent, ClientType.ADVERSARY);
		final String titleAdv = "Recherche adversaire";
		final String descriptionAdv = "Cette fenetre permet de rechercher un adversaire";
		final SearchContext searchContextAdv = new SearchContext(advDataListModel, labelProvider, titleAdv, descriptionAdv);

		final ObjectSeekComposite advSeekComposite = new ObjectSeekComposite(composite, searchContextAdv);
		advSeekComposite.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 2, 1));
		bindingSupport.bindObjectSeekComposite(customizerModel, "adversary", advSeekComposite, IValidationSupport.NOT_EMPTY_VALIDATOR);

		final Label natureClientLabel = new Label(composite, SWT.NONE);
		natureClientLabel.setText("Qualité:");

		final ComboViewer natureComboViewer = new ComboViewer(composite, SWT.READ_ONLY);
		final Combo combo = natureComboViewer.getCombo();
		combo.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 2, 1));

		natureComboViewer.setContentProvider(ArrayContentProvider.getInstance());
		natureComboViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(final Object element) {
				return ((Nature) element).getNatureName();
			}
		});
		List<Nature> natures = BundleUtil.getService(IServiceDao.class).getAll(Nature.class);
		if (natures == null || natures.isEmpty()) {
			natures = buildClientNature();
		}
		natureComboViewer.setInput(natures);

		bindingSupport.bindComboViewer(customizerModel, "clientNature", natureComboViewer, IValidationSupport.NOT_EMPTY_VALIDATOR);

		final Label lblAvocat2emePartie = new Label(composite, SWT.NONE);
		lblAvocat2emePartie.setText("Avocat 2eme partie:");
		final Text avocateme2PartieText = new Text(composite, SWT.BORDER);
		avocateme2PartieText.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 2, 1));

		new TitledSeparator(composite, "Conflit").setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 6, 1));

		final Label juridictionLabel = new Label(composite, SWT.NONE);
		juridictionLabel.setText("Juridiction:");

		final LabelProvider courtLabelProvider = new LabelProvider() {
			@Override
			public String getText(final Object element) {
				final Court court = (Court) element;
				final CourtType courtType = court.getType();
				String type = courtType.name();
				switch (courtType) {
					case TRIBUNAL:
						type = "Tribunal";
						break;
					case COUR:
						type = "Cour";
						break;
					case COURS_SUPREME:
						type = "Cour suprème";
						break;

					case CONSEIL_ETAT:
						type = "Conseil d'Etat";
						break;

					case TRIBUNAL_ADMINISTRATIF:
						type = "Tribunal Administratif";
						break;
					default:
						type = "Tribunal";
						break;
				}
				return type + " " + court.getCity();
			}
		};

		final DataListModel courtDataListModel = new CourtDataListModel(parent);
		final String titleCourt = "Recherche juridiction";
		final String descriptionCourt = "Cette fenetre permet de rechercher une juridiction";
		final SearchContext searchContextCourt = new SearchContext(courtDataListModel, courtLabelProvider, titleCourt, descriptionCourt);

		final ObjectSeekComposite courtSeekComposite = new ObjectSeekComposite(composite, searchContextCourt);
		courtSeekComposite.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 2, 1));
		bindingSupport.bindObjectSeekComposite(customizerModel, "court", courtSeekComposite, IValidationSupport.NOT_EMPTY_VALIDATOR);

		final Label sectionLabel = new Label(composite, SWT.NONE);
		sectionLabel.setText("Section:");

		final ComboViewer sectionComboViewer = new ComboViewer(composite, SWT.READ_ONLY);
		final Combo comboSection = sectionComboViewer.getCombo();
		comboSection.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, false, false, 2, 1));

		sectionComboViewer.setContentProvider(ArrayContentProvider.getInstance());
		sectionComboViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(final Object element) {
				return ((Section) element).getSectionName();
			}
		});
		List<Section> sections = BundleUtil.getService(IServiceDao.class).getAll(Section.class);
		if (sections == null || sections.isEmpty()) {
			sections = buildSections();
		}
		sectionComboViewer.setInput(sections);

		bindingSupport.bindComboViewer(customizerModel, "section", sectionComboViewer, IValidationSupport.NOT_EMPTY_VALIDATOR);

		final Label sujetLabel = new Label(composite, SWT.NONE);
		sujetLabel.setText("Objet:");

		final Text text = new Text(composite, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 2, 1));

		final Label commentaireLabel = new Label(composite, SWT.NONE);
		commentaireLabel.setText("Commentaire:");

		final Text commentaireText = new Text(composite, SWT.BORDER);
		commentaireText.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 2, 1));
		bindingSupport.bindText(customizerModel, "comment", commentaireText, IValidationSupport.NOT_EMPTY_VALIDATOR);

		new TitledSeparator(composite, "Détails").setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 6, 1));

		final Label startDateLabel = new Label(composite, SWT.NONE);
		startDateLabel.setText("Date d'entrée:");

		final DateTime dtStartDate = new DateTime(composite, SWT.BORDER | SWT.DATE | SWT.DROP_DOWN);
		dtStartDate.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 2, 1));
		bindingSupport.bindDateTimeChooserComboWidget(customizerModel, "startDate", dtStartDate, IValidationSupport.NOT_EMPTY_VALIDATOR);

		final Label honoraireLabel = new Label(composite, SWT.NONE);
		honoraireLabel.setText("Honoraire:");

		final Text honoraireText = new Text(composite, SWT.BORDER);
		honoraireText.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 2, 1));
		bindingSupport.bindText(customizerModel, "honorary", honoraireText, IValidationSupport.NOT_EMPTY_VALIDATOR);

		Label judgmentDateLabel = new Label(composite, SWT.NONE);
		judgmentDateLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		judgmentDateLabel.setText("Date jugement:");

		DateTime judgmentDate = new DateTime(composite, SWT.BORDER | SWT.DATE | SWT.DROP_DOWN);
		judgmentDate.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 2, 1));

		Label archiveIdLabel = new Label(composite, SWT.NONE);
		archiveIdLabel.setText("Numéro archive:");

		Text archiveId = new Text(composite, SWT.BORDER);
		archiveId.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 2, 1));

		return composite;
	}

	@Override
	public void validateUpdate() {
		customizerModel.validate();
	}

	@Override
	public void cancelUpdate() {
		// Ask the user to confirm the cancellation
	}

	private List<Nature> buildClientNature() {
		final List<Nature> natures = new ArrayList<Nature>();
		natures.add(new Nature("Demandeur"));
		natures.add(new Nature("Défendeur"));
		natures.add(new Nature("Appelant"));
		natures.add(new Nature("Intimé"));
		natures.add(new Nature("Demandeur au pourvoi"));
		natures.add(new Nature("Defendeur au pourvoi"));
		natures.add(new Nature("Prévenu"));
		natures.add(new Nature("Partie civil"));
		natures.add(new Nature("Mis en cause"));
		natures.add(new Nature("Intervenant"));
		// save default natures
		return BundleUtil.getService(IServiceDao.class).saveAll(natures);
	}

	private List<Section> buildSections() {
		final List<Section> sections = new ArrayList<Section>();
		sections.add(new Section("Civil"));
		sections.add(new Section("Penal"));
		sections.add(new Section("Administratif"));
		sections.add(new Section("Foncier"));
		sections.add(new Section("Comercial"));
		sections.add(new Section("Statut Personnel"));
		sections.add(new Section("Référé"));
		sections.add(new Section("Référé Administratif"));
		sections.add(new Section("Social"));
		sections.add(new Section("Contravention"));
		sections.add(new Section("Criminel"));
		sections.add(new Section("Mineur"));
		sections.add(new Section("Chambre d'accusation"));
		// save default sections
		return BundleUtil.getService(IServiceDao.class).saveAll(sections);
	}
}
