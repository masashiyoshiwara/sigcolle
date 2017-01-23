package net.unit8.sigcolle.controller;

import enkan.component.doma2.DomaProvider;
import enkan.data.Flash;
import enkan.data.HttpResponse;
import enkan.data.Session;
import kotowari.component.TemplateEngine;
import net.unit8.sigcolle.dao.CampaignDao;
import net.unit8.sigcolle.dao.SignatureDao;
import net.unit8.sigcolle.dao.UserDao;
import net.unit8.sigcolle.form.CampaignForm;
import net.unit8.sigcolle.form.SignatureForm;
import net.unit8.sigcolle.model.Campaign;
import net.unit8.sigcolle.model.Signature;
import net.unit8.sigcolle.model.User;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.IOException;

import static enkan.util.BeanBuilder.builder;
import static enkan.util.HttpResponseUtils.RedirectStatusCode.SEE_OTHER;
import static enkan.util.HttpResponseUtils.redirect;
import static enkan.util.HttpResponseUtils.resourceResponse;
import static enkan.util.ThreadingUtils.some;

/**
 * @author Masashi Yoshiwara
 */
public class RegisterCampaignController {
    @Inject
    TemplateEngine templateEngine;

    @Inject
    DomaProvider domaProvider;

    public HttpResponse index(CampaignForm form, Flash flash,Session session) throws IOException {

        if(session.get("userId") == null){
        return templateEngine.render("index",
                "login", new CampaignForm()
        );
    }
        return templateEngine.render("registerCampaign",
                "campaign", new CampaignForm()
        );
    }

    @Transactional
    public HttpResponse register(CampaignForm form, Session session) throws IOException {

        if (form.hasErrors()) {
            return templateEngine.render("register",
                    "campaign", form
            );
        }

        CampaignDao campaignDao = domaProvider.getDao(CampaignDao.class);

        Campaign campaign = builder(new Campaign())
                .set(Campaign::setTitle, form.getTitle())
                .set(Campaign::setStatement, form.getStatement())
                .set(Campaign::setGoal, form.getGoalLong())
                .set(Campaign::setCreatedBy, form.getCreatedByLong())
                .build();
        campaignDao.insert(campaign);

        return builder(redirect("/", SEE_OTHER))
                .set(HttpResponse::setSession, session)
                .build();
    }
}
