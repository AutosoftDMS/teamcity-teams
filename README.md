# teamcity-teams

A configurable TeamCity plugin that notifies a [Microsoft Teams](https://products.office.com/en-us/microsoft-teams/) webhook.
Because it is a [TeamCity Custom Notifier](http://confluence.jetbrains.com/display/TCD10/Custom+Notifier) plugin, it extends the existing user interface and allows for easy configuration directly within your TeamCity server. Once installed, you can configure the plugin for multiple TeamCity projects and multiple build conditions (i.e. Build failures, successes, hangs, etc.)

## Installation
Download the [plugin zip package](https://github.com/enlivenhq/teamcity-slack/releases/download/1.0/teamcity-slack-integration-1.0.zip).

Follow the TeamCity [plugin installation directions](http://confluence.jetbrains.com/display/TCD10/Installing+Additional+Plugins).

## Configuration

In Microsoft Teams, right-click on a channel and select "Connectors". Then click "Add" next to "Incoming Webhook".

Provide a name and upload an image. There are some TeamCity logos under the [/images/](images folder).

Copy the URL for the webhook.

As an admin, Navigate to your TeamCity profile page ("My Settings & Tools") and click "Edit".

Enter the webhook URL in the Notification settings.

Add notification rules as appropriate.

## Disclaimer

Tested mostly with TeamCity version 10.0.4 but it should work with 9.1 and above.

## Thanks

Thanks to the [enlivenhq/teamcity-slack](https://github.com/enlivenhq/teamcity-slack) plugin that this was based on.

## License
MIT
