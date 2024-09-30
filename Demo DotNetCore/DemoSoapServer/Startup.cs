using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.HttpsPolicy;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.Extensions.Logging;
using SoapCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Threading.Tasks;
using DemoSoapServer;
using DemoSoapServer.Controllers;

namespace DemoSoapServer
{
    public class Startup
    {
        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        public IConfiguration Configuration { get; }

        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services)
        {
            services.AddControllers()
                .AddXmlSerializerFormatters(); //Added to support XMLSerialization

            //Add/register SituationPublicationService to IOC container
            services.AddScoped<DemoSoapServer.snapshotPullInterface, SituationPublicationService>();
            services.AddScoped<Data.IDataManager, Data.DataManager>();

            services.AddSoapCore();
            
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IWebHostEnvironment env)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }

            //Configure soapCore endpoint
            #region
            //Read settings from appSettings
            var settings = Configuration.GetSection("FileWSDL").Get<WsdlFileOptions>();
            //Set app path
            settings.AppPath = env.ContentRootPath;

            var binding = new BasicHttpBinding
            {
                TextEncoding = new System.Text.UTF8Encoding(false) //parameter no byte order mark, for compatibillity
            };
          
            app.UseSoapEndpoint<DemoSoapServer.snapshotPullInterface>("/SituationService", new SoapEncoderOptions(), SoapSerializer.XmlSerializer, false, null, settings);
            #endregion

            app.UseHttpsRedirection();

            app.UseRouting();

            app.UseAuthorization();

            app.UseEndpoints(endpoints =>
            {
                endpoints.MapControllers();
            });
        }
    }
}
