package com.mobium.testproject.data.components;

import com.mobium.testproject.data.JobsApi;
import com.mobium.testproject.data.modules.JobsModule;
import com.mobium.testproject.data.modules.PicassoModule;
import com.mobium.testproject.data.scopes.JobsApplicationScope;
import com.squareup.picasso.Picasso;

import dagger.Component;

@JobsApplicationScope
@Component(modules = {JobsModule.class, PicassoModule.class})
public interface JobsComponent {
    JobsApi getJobsService();
    Picasso getPicasso();
}
