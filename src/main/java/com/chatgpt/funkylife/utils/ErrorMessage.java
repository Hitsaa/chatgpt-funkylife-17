package com.chatgpt.funkylife.utils;

public abstract class ErrorMessage {

	public static final String SOMETHING_WENT_WRONG = "Something went wrong";
	public static final String ACCESS_DENIED = "Access denied. Confirmation required.";

	public static final String EMAIL_NOT_EXISTS = "Email address does not exist.";
	public static final String EMAIL_ALREADY_EXISTS = "This email address already exists.";
	public static final String NO_SUCH_USER = "No such user exists.";

	public static final String CODE_EXPIRED = "Expired code. Try again.";
	public static final String CODE_INCORRECT = "Incorrect code. Try again.";

	public static final String INVALID_ID = "Invalid ID";
	public static final String INVALID_ROUTE = "Invalid route";
	public static final String INVALID_NICHE = "Invalid niche";
	public static final String INVALID_COLOUR = "Invalid colour";
	public static final String NICHE_REQUIRED = "Niche is required";
	public static final String INVALID_DOMAIN = "Domain is invalid";

	public static final String DOMAIN_NOT_AVAILABLE = "Domain name is not available.";

	public static final String USERNAME_EXISTS = "Username already exists. Try a different one.";

	public static final String MALFORMED_REQUEST = "Invalid request.";

	public static final String INVALID_SLUG = "Invalid slug";
	public static final String NO_SUCH_POST = "Blog post not found.";

	public static final String CARD_ALREADY_ADDED = "This payment method already exists.";
	public static final String INVALID_PLAN = "Invalid plan";

	public static final String INVALID_SUBSCRIPTION = "Subscription invalid.";
	public static final String SUBSCRIPTION_EXISTS = "Subscription already exists";

	public static final String NO_SUCH_CARD = "No payment method exists.";
	public static final String NO_SUCH_CUSTOMER = "Customer not found";

	public static final String NO_SUCH_PAGE = "Page not found";
	public static final String BLOG_SLUG_NOT_AVAILABLE = "Blog post URL not available. Try another.";

	public static final String PAGE_SLUG_NOT_AVAILABLE = "Page URL not available. Try another.";

	public static final String NO_CARD_ADDED = "Please add a payment method (debit/credit card)";
	public static final String INSUFFICIENT_STORAGE = "Storage limit reached. Upgrade plan to increase limits.";
	public static final String SITES_CREATION_LIMIT_EXHAUSTED = "Site limit reached! Upgrade plan to increase limit instantly.";

	public static final String SUBSCRIPTION_CREATION_FAILED = "Please update your payment method";
	public static final String UNAUTHORIZED = "Unauthorized";
	public static final String SUBSCRIPTION_REQUIRED = "Your account is inactive. Go to Billing to activate.";
	public static final String DISCOUNT_ALREADY_TAKEN = "Discount is invalid (one-time use only)";

	public static final String PLAN_NOT_AVAILABLE = "You have too many sites for this plan. Delete some first.";
	public static final String SUBSCRIPTION_ALREADY_CANCELLED = "Your subscription is already cancelled.";

	public static final String DOMAIN_ALREADY_EXISTS = "Domain already exists.";
	public static final String PLAN_UPGRADE_REQUIRED = "Your site views are above this plan's limit.";

	public static final String CATEGORY_MAX_ERROR = "Maximum 3 categories are allowed!";
	public static final String CATEGORY_MIN_ERROR = "Atleast 1 category is required!";
	
	public static final String ALREADY_CONFIRMED = "You are confirmed!";
	public static final String CUSTOM_DOMAIN_NOT_ADDED = "Custom domain is not added";
	
	public static final String INVALID_CREDENTIALS = "Email and/or password is invalid.";
	
	public static final String REFUND_FAILED = "Refund failed";
	public static final String SUBSCRIPTION_CANCELLATION_FAILED = "Subscription cancellation failed";
	public static final String SUBSCRIPTION_PAUSE_FAILED = "Subscription Pause failed";

	public static final String AUTHOR_NAME_ERROR ="Author Name already exists";

	public static final String SUBSCRIPTION_UNFREEZE_FAILED = "Subscription Unfreeze failed";

	public static final String ACTIVE_CAMPAIGN_ERROR = "Active Campaign Credential not Present!";
	public static final String ACTIVE_CAMPAIGN_LIST_ERROR = "Active Campaign List no longer exists.";
	public static final String NO_DEFAULT_PAGE_DELETED = "Default page cannot be deleted";

	public static final String TWO_HOME_PAGE_ERROR = "Home page can only be one. please choose one home page.";

	public static final String NO_HOME_PAGE_FOUND = "Home page not found by given url ";

	public static final String SUBMENU_LINKS_EXIST = "Sub menu links exist in menu for this page so it cannot be set as home page.";
	public static final String BLOG_SLUG_RESERVED = "blog url is reserved for blog listing page. It can be used with only Blog Home page provided it is not set as home page.";

	public static final String QUERY_NOT_APPROPRIATE = "The given query is not appropriate. please specify query for short results.";
}
